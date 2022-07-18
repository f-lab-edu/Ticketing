package com.ticketing.server.movie.application;

import static com.ticketing.server.user.domain.UserGrade.ROLES.USER;

import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.movie.application.response.TicketListResponse;
import com.ticketing.server.movie.service.dto.TicketDTO;
import com.ticketing.server.movie.service.interfaces.TicketService;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
@Validated
public class TicketController {

	private final TicketService ticketService;

	@GetMapping
	@Secured(USER)
	public ResponseEntity<TicketListResponse> getTickets(
		@ApiParam(value = "영화 시간표 ID", required = true) @RequestParam @NotNull Long movieTimeId) {
		List<TicketDTO> tickets = ticketService.getTickets(movieTimeId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				new TicketListResponse(tickets)
			);
	}

	@GetMapping("/payments/{paymentId}")
	@Secured(USER)
	public ResponseEntity<TicketDetailsResponse> findTicketsByPaymentId(
		@PathVariable("paymentId") @NotNull Long paymentId) {
		List<TicketDetailDTO> tickets = ticketService.findTicketsByPaymentId(paymentId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				new TicketDetailsResponse(tickets)
			);
	}

}
