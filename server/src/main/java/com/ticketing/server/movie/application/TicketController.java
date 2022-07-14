package com.ticketing.server.movie.application;

import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.interfaces.TicketService;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
@Validated
public class TicketController {

	private final TicketService ticketService;

	@GetMapping("/payments/{paymentId}")
	public ResponseEntity<TicketDetailsResponse> findTicketsByPaymentId(@PathVariable("paymentId") @NotNull Long paymentId) {
		TicketDetailsDTO tickets = ticketService.findTicketsByPaymentId(paymentId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(tickets.toResponse());
	}

}
