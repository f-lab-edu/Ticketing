package com.ticketing.server.movie.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketListDTO {

	private final List<TicketDTO> ticketDtos;

	public TicketListResponse toResponse() {
		return new TicketListResponse(ticketDtos);
	}

}
