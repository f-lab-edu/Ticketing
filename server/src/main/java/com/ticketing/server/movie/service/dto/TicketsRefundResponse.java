package com.ticketing.server.movie.service.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketsRefundResponse {

	private final List<TicketRefundDTO> tickets;

	public List<Long> getTicketIds() {
		return tickets.stream()
			.mapToLong(TicketRefundDTO::getTicketId)
			.boxed()
			.collect(Collectors.toList());
	}

}
