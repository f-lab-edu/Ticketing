package com.ticketing.server.movie.service;

import static com.ticketing.server.global.exception.ErrorCode.INVALID_TICKET_ID;
import static com.ticketing.server.global.exception.ErrorCode.MOVIE_TIME_NOT_FOUND;
import static com.ticketing.server.global.exception.ErrorCode.PAYMENT_ID_NOT_FOUND;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.movie.service.dto.TicketDTO;
import com.ticketing.server.movie.service.dto.TicketIdsDTO;
import com.ticketing.server.movie.service.dto.TicketRefundDTO;
import com.ticketing.server.movie.service.dto.TicketsCancelDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.movie.service.dto.TicketsSoldDTO;
import com.ticketing.server.movie.service.interfaces.TicketService;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	private final TicketLockService ticketLockService;

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
		return ticketLockService.ticketReservation(new TicketIdsDTO(ticketIds));
	}

	@Override
	@Transactional
	public TicketsSoldDTO ticketSold(@NotNull Long paymentId, @NotEmptyCollection List<Long> ticketIds) {
		return ticketLockService.ticketSold(paymentId, new TicketIdsDTO(ticketIds));
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

	private List<Ticket> getTicketsByInTicketIds(List<Long> ticketIds) {
		List<Ticket> tickets = ticketRepository.findTicketFetchJoinByTicketIds(ticketIds);

		if (tickets.size() != ticketIds.size()) {
			throw new TicketingException(INVALID_TICKET_ID);
		}

		return tickets;
	}

}
