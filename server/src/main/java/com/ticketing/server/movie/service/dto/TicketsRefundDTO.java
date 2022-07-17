package com.ticketing.server.movie.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketsRefundDTO {

	private List<TicketRefundDTO> tickets;

	public TicketsRefundResponse toResponse() {
		return new TicketsRefundResponse(tickets);
	}
}
