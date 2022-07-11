package com.ticketing.server.payment.api;

import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import javax.validation.constraints.NotNull;

public interface MovieClient {

	TicketDetailsResponse getTicketsByPaymentId(@NotNull Long paymentId);

}
