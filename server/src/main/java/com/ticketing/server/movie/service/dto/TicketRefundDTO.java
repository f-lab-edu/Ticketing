package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.TicketStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketRefundDTO {

	private Long ticketId;
	private TicketStatus ticketStatus;

	public TicketRefundDTO(Ticket ticket) {
		this(ticket.getId(), ticket.getStatus());
	}

}
