package com.ticketing.server.payment.api.impl;

import com.ticketing.server.movie.application.request.TicketCancelRequest;
import com.ticketing.server.movie.application.request.TicketReservationRequest;
import com.ticketing.server.movie.application.request.TicketSoldRequest;
import com.ticketing.server.movie.application.response.TicketCancelResponse;
import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.movie.application.response.TicketReservationResponse;
import com.ticketing.server.movie.application.response.TicketSoldResponse;
import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.dto.TicketsCancelDTO;
import com.ticketing.server.movie.service.dto.TicketsRefundDTO;
import com.ticketing.server.movie.service.dto.TicketsRefundResponse;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.movie.service.dto.TicketsSoldDTO;
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

	@Override
	public TicketReservationResponse ticketReservation(@NotNull TicketReservationRequest request) {
		TicketsReservationDTO ticketReservationsDto = ticketService.ticketReservation(request.getTicketIds());
		return ticketReservationsDto.toResponse();
	}

	@Override
	public TicketSoldResponse ticketSold(@NotNull TicketSoldRequest request) {
		TicketsSoldDTO ticketsSoldDto = ticketService.ticketSold(request.getPaymentId(), request.getTicketIds());
		return ticketsSoldDto.toResponse();
	}

	@Override
	public TicketCancelResponse ticketCancel(@NotNull TicketCancelRequest request) {
		TicketsCancelDTO ticketsCancelDto = ticketService.ticketCancel(request.getTicketIds());
		return ticketsCancelDto.toResponse();
	}

	@Override
	public TicketsRefundResponse myTicketRefund(TicketsRefundRequest request) {
		TicketsRefundDTO ticketsRefundDto = ticketService.myTicketsRefund(request.getPaymentId());
		return ticketsRefundDto.toResponse();
	}

}
