package com.ticketing.server.payment.api.impl;

import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.interfaces.TicketService;
import com.ticketing.server.payment.api.MovieClient;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class MovieClientImpl implements MovieClient {

	private final TicketService ticketService;

	@Override
	public TicketDetailsResponse getTicketsByPaymentId(@NotNull Long paymentId) {
		TicketDetailsDTO ticketDetails = ticketService.findTicketsByPaymentId(paymentId);
		return ticketDetails.toResponse();
	}

}
