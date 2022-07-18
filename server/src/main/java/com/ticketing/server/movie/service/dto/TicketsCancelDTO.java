package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.TicketCancelResponse;
import com.ticketing.server.movie.domain.Ticket;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TicketsCancelDTO {

	private final List<Long> ticketIds;

	public TicketsCancelDTO(List<Ticket> tickets) {
		ticketIds = tickets.stream()
			.map(Ticket::getId)
			.collect(Collectors.toList());
	}

	public TicketCancelResponse toResponse() {
		return new TicketCancelResponse(ticketIds);
	}

}
