package com.ticketing.server.movie.service;

import static com.ticketing.server.global.exception.ErrorCode.BAD_REQUEST_MOVIE_TIME;
import static com.ticketing.server.global.exception.ErrorCode.INVALID_TICKET_ID;
import static com.ticketing.server.global.exception.ErrorCode.MOVIE_TIME_NOT_FOUND;
import static com.ticketing.server.global.exception.ErrorCode.PAYMENT_ID_NOT_FOUND;
import static com.ticketing.server.movie.domain.TicketLock.LOCK_KEY;
import static com.ticketing.server.movie.domain.TicketLock.LOCK_VALUE;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.movie.service.dto.TicketDTO;
import com.ticketing.server.movie.service.dto.TicketRefundDTO;
import com.ticketing.server.movie.service.dto.TicketReservationDTO;
import com.ticketing.server.movie.service.dto.TicketSoldDTO;
import com.ticketing.server.movie.service.dto.TicketsCancelDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.movie.service.dto.TicketsSoldDTO;
import com.ticketing.server.movie.service.interfaces.TicketService;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
@Slf4j
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;
	private final MovieTimeRepository movieTimeRepository;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public List<TicketDTO> getTickets(@NotNull Long movieTimeId) {
		MovieTime movieTime = movieTimeRepository.findById(movieTimeId)
			.orElseThrow(() -> new TicketingException(MOVIE_TIME_NOT_FOUND));

		return ticketRepository.findValidTickets(movieTime)
			.stream()
			.map(TicketDTO::new)
			.collect(Collectors.toList());
	}

	@Override
	public List<TicketDetailDTO> findTicketsByPaymentId(@NotNull Long paymentId) {
		List<TicketDetailDTO> ticketDetails = ticketRepository.findTicketFetchJoinByPaymentId(paymentId)
			.stream()
			.map(TicketDetailDTO::new)
			.collect(Collectors.toList());

		if (ticketDetails.isEmpty()) {
			throw new TicketingException(PAYMENT_ID_NOT_FOUND);
		}

		return ticketDetails;
	}

	@Override
	@Transactional
	public TicketsReservationDTO ticketReservation(@NotEmptyCollection List<Long> ticketIds) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIds);
		List<String> ticketLockIds = makeTicketLockIds(ticketIds);

		try {
			if (!isEveryTicketIdLock(ticketLockIds)) {
				throw new TicketingException(ErrorCode.BAD_REQUEST_TICKET_RESERVATION);
			}

			Long firstMovieTimeId = firstMovieTimeId(tickets);
			List<TicketReservationDTO> reservationDtoList = tickets.stream()
				.map(Ticket::makeReservation)
				.filter(ticket -> firstMovieTimeId.equals(ticket.getMovieTimeId()))
				.map(TicketReservationDTO::new)
				.collect(Collectors.toList());

			if (ticketIds.size() != reservationDtoList.size()) {
				throw new TicketingException(BAD_REQUEST_MOVIE_TIME);
			}

			return new TicketsReservationDTO(firstMovieTitle(tickets), reservationDtoList);
		} finally {
			ticketIdsUnlock(ticketLockIds);
		}
	}

	@Override
	@Transactional
	public TicketsSoldDTO ticketSold(@NotNull Long paymentId, @NotEmptyCollection List<Long> ticketIds) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIds);
		List<String> ticketLockIds = makeTicketLockIds(ticketIds);

		try {
			if (!isEveryTicketIdLock(ticketLockIds)) {
				throw new TicketingException(ErrorCode.BAD_REQUEST_TICKET_SOLD);
			}

			List<TicketSoldDTO> soldDtoList = tickets.stream()
				.map(ticket -> ticket.makeSold(paymentId))
				.map(TicketSoldDTO::new)
				.collect(Collectors.toList());

			return new TicketsSoldDTO(paymentId, soldDtoList);
		} finally {
			ticketIdsUnlock(ticketLockIds);
		}
	}

	@Override
	@Transactional
	public TicketsCancelDTO ticketCancel(@NotEmptyCollection List<Long> ticketIds) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIds);
		tickets.forEach(Ticket::cancel);

		return new TicketsCancelDTO(tickets);
	}

	@Override
	@Transactional
	public List<TicketRefundDTO> ticketsRefund(@NotNull Long paymentId, UnaryOperator<Ticket> refund) {
		List<Ticket> tickets = ticketRepository.findTicketFetchJoinByPaymentId(paymentId);

		return tickets.stream()
			.map(refund)
			.map(TicketRefundDTO::new)
			.collect(Collectors.toList());
	}

	protected boolean isEveryTicketIdLock(List<String> ids) {
		for (String id : ids) {
			if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(id, LOCK_VALUE.getValue(), 5, TimeUnit.MINUTES))) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	protected Long ticketIdsUnlock(List<String> ids) {
		return redisTemplate.delete(ids);
	}

	private List<String> makeTicketLockIds(List<Long> ticketIds) {
		return ticketIds.stream()
			.map(id -> LOCK_KEY.getValue() + ":" + id)
			.collect(Collectors.toList());
	}

	private List<Ticket> getTicketsByInTicketIds(List<Long> ticketIds) {
		List<Ticket> tickets = ticketRepository.findTicketFetchJoinByTicketIds(ticketIds);

		if (tickets.size() != ticketIds.size()) {
			throw new TicketingException(INVALID_TICKET_ID);
		}

		return tickets;
	}

	private Long firstMovieTimeId(List<Ticket> tickets) {
		Ticket ticket = tickets.get(0);
		return ticket.getMovieTimeId();
	}

	private String firstMovieTitle(List<Ticket> tickets) {
		Ticket ticket = tickets.get(0);
		return ticket.getMovieTitle();
	}

}
