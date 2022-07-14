package com.ticketing.server.movie.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.movie.service.dto.TicketDTO;
import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.dto.TicketListDTO;
import com.ticketing.server.movie.service.dto.TicketReservationDTO;
import com.ticketing.server.movie.service.dto.TicketSoldDTO;
import com.ticketing.server.movie.service.dto.TicketsCancelDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.movie.service.dto.TicketsSoldDTO;
import com.ticketing.server.movie.service.interfaces.TicketService;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.util.List;
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

	@Override
	public TicketListDTO getTickets(@NotNull Long movieTimeId) {
		MovieTime movieTime = movieTimeRepository.findById(movieTimeId)
			.orElseThrow(ErrorCode::throwMovieTimeNotFound);

		List<TicketDTO> tickets = ticketRepository.findValidTickets(movieTime)
			.stream()
			.map(TicketDTO::new)
			.collect(Collectors.toList());

		return new TicketListDTO(tickets);
	}

	@Override
	public TicketDetailsDTO findTicketsByPaymentId(@NotNull Long paymentId) {
		List<TicketDetailDTO> ticketDetails = ticketRepository.findTicketFetchJoinByPaymentId(paymentId)
			.stream()
			.map(TicketDetailDTO::new)
			.collect(Collectors.toList());

		if (ticketDetails.isEmpty()) {
			throw ErrorCode.throwPaymentIdNotFound();
		}

		return new TicketDetailsDTO(ticketDetails);
	}

	@Override
	@Transactional
	public TicketsReservationDTO ticketReservation(@NotEmptyCollection List<Long> ticketIds) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIds);

		Long firstMovieTimeId = firstMovieTimeId(tickets);
		List<TicketReservationDTO> reservationDtoList = tickets.stream()
			.map(Ticket::makeReservation)
			.filter(ticket -> firstMovieTimeId.equals(ticket.getMovieTimeId()))
			.map(TicketReservationDTO::new)
			.collect(Collectors.toList());

		if (ticketIds.size() != reservationDtoList.size()) {
			throw ErrorCode.throwBadRequestMovieTime();
		}

		return new TicketsReservationDTO(firstMovieTitle(tickets), reservationDtoList);
	}

	@Override
	@Transactional
	public TicketsSoldDTO ticketSold(@NotNull Long paymentId, @NotEmptyCollection List<Long> ticketIds) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIds);

		List<TicketSoldDTO> soldDtoList = tickets.stream()
			.map(ticket -> ticket.makeSold(paymentId))
			.map(TicketSoldDTO::new)
			.collect(Collectors.toList());

		return new TicketsSoldDTO(paymentId, soldDtoList);
	}

	@Override
	@Transactional
	public TicketsCancelDTO ticketCancel(@NotEmptyCollection List<Long> ticketIds) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIds);
		tickets.forEach(Ticket::cancel);

		return new TicketsCancelDTO(ticketIds);
	}

	private List<Ticket> getTicketsByInTicketIds(List<Long> ticketIds) {
		List<Ticket> tickets = ticketRepository.findTicketFetchJoinByTicketIds(ticketIds);

		if (tickets.size() != ticketIds.size()) {
			throw ErrorCode.throwInvalidTicketId();
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
