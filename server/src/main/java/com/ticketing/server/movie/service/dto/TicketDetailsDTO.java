package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.payment.application.response.TicketDetailDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketDetailsDTO {

	private final List<TicketDetailDTO> ticketDetails;

	public TicketDetailsResponse toResponse() {
		return new TicketDetailsResponse(ticketDetails);
	}
}
