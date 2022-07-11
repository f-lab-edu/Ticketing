package com.ticketing.server.movie.application.response;

import com.ticketing.server.payment.application.response.TicketDetailDTO;
import java.util.List;
import lombok.Getter;

@Getter
public class TicketDetailsResponse {

	private final List<TicketDetailDTO> ticketDetails;

	public TicketDetailsResponse(List<TicketDetailDTO> ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

}
