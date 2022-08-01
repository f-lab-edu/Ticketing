package com.ticketing.server.movie.service;

import static com.ticketing.server.global.exception.ErrorCode.BAD_REQUEST_MOVIE_TIME;
import static com.ticketing.server.global.exception.ErrorCode.INVALID_TICKET_ID;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.movie.service.dto.TicketIdsDTO;
import com.ticketing.server.movie.service.dto.TicketReservationDTO;
import com.ticketing.server.movie.service.dto.TicketSoldDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.movie.service.dto.TicketsSoldDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class TicketLockService {

	private final TicketRepository ticketRepository;

	public TicketsReservationDTO ticketReservation(@Valid TicketIdsDTO ticketIdsDto) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIdsDto.getTicketIds());

		Long firstMovieTimeId = firstMovieTimeId(tickets);
		List<TicketReservationDTO> reservationDtoList = tickets.stream()
			.map(Ticket::makeReservation)
			.filter(ticket -> firstMovieTimeId.equals(ticket.getMovieTimeId()))
			.map(TicketReservationDTO::new)
			.collect(Collectors.toList());

		if (tickets.size() != reservationDtoList.size()) {
			throw new TicketingException(BAD_REQUEST_MOVIE_TIME);
		}

		return new TicketsReservationDTO(firstMovieTitle(tickets), reservationDtoList);
	}

	public TicketsSoldDTO ticketSold(@NotNull Long paymentId, @Valid TicketIdsDTO ticketIdsDto) {
		List<Ticket> tickets = getTicketsByInTicketIds(ticketIdsDto.getTicketIds());

		List<TicketSoldDTO> soldDtoList = tickets.stream()
			.map(ticket -> ticket.makeSold(paymentId))
			.map(TicketSoldDTO::new)
			.collect(Collectors.toList());

		return new TicketsSoldDTO(paymentId, soldDtoList);
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
