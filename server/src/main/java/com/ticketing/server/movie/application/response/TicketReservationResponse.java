package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.service.dto.TicketReservationDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketReservationResponse {

	private final String movieTitle;
	private final List<TicketReservationDTO> tickets;

	public int getTicketQuantity() {
		return tickets.size();
	}

	public int getTotalPrice() {
		return tickets.stream()
			.mapToInt(TicketReservationDTO::getTicketPrice)
			.sum();
	}

}
