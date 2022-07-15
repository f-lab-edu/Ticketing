package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.TicketListResponse;
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
