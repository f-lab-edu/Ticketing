package com.ticketing.server.payment.service.dto;

import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.payment.application.response.PaymentDetailResponse;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentDetailDTO {

	private final Long paymentId;
	private final String movieTitle;
	private final String paymentNumber;
	private final Integer totalPrice;
	private final LocalDateTime createdAt;
	private final List<TicketDetailDTO> tickets;

	public PaymentDetailDTO(Payment payment, TicketDetailsResponse ticketDetails) {
		this(
			payment.getId(),
			payment.getMovieTitle(),
			payment.getPaymentNumber(),
			payment.getTotalPrice(),
			payment.getCreatedAt(),
			ticketDetails.getTicketDetails()
		);
	}

	public PaymentDetailResponse toResponse() {
		return new PaymentDetailResponse(paymentId, movieTitle, paymentNumber, totalPrice, createdAt, tickets);
	}

}
