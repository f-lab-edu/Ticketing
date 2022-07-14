package com.ticketing.server.payment.api;

import com.ticketing.server.movie.application.request.TicketCancelRequest;
import com.ticketing.server.movie.application.request.TicketReservationRequest;
import com.ticketing.server.movie.application.response.TicketCancelResponse;
import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.movie.application.response.TicketReservationResponse;
import com.ticketing.server.movie.application.request.TicketSoldRequest;
import com.ticketing.server.movie.application.response.TicketSoldResponse;
import javax.validation.constraints.NotNull;

public interface MovieClient {

	TicketDetailsResponse getTicketsByPaymentId(@NotNull Long paymentId);

	TicketReservationResponse ticketReservation(@NotNull TicketReservationRequest request);

	TicketSoldResponse ticketSold(@NotNull TicketSoldRequest request);

	TicketCancelResponse ticketCancel(@NotNull TicketCancelRequest request);
}
