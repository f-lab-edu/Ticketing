package com.ticketing.server.movie.application.response;

import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.util.List;
import lombok.Getter;

@Getter
public class TicketDetailsResponse {

	private final List<TicketDetailDTO> ticketDetails;

	public TicketDetailsResponse(List<TicketDetailDTO> ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

}
