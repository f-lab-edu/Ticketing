package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.domain.Ticket;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketSoldDTO {

	private final Long ticketId;
	private final Integer ticketPrice;

	public TicketSoldDTO(Ticket ticket) {
		this(ticket.getId(), ticket.getTicketPrice());
	}

}
