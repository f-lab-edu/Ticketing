package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.TicketCancelResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketsCancelDTO {

	private final List<Long> ticketIds;

	public TicketCancelResponse toResponse() {
		return new TicketCancelResponse(ticketIds);
	}

}
