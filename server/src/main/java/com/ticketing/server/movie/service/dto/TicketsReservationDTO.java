package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.TicketReservationResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketsReservationDTO {

	private final String movieTitle;
	private final List<TicketReservationDTO> ticketReservationDtoList;

	public TicketReservationResponse toResponse() {
		return new TicketReservationResponse(movieTitle, ticketReservationDtoList);
	}

}
