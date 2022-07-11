package com.ticketing.server.payment.application.response;

import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.payment.domain.Payment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentDetailResponse {

	private Long paymentId;

	private String movieTitle;

	private String paymentNumber;

	private Integer totalPrice;

	private LocalDateTime createdAt;

	List<TicketDetailDTO> tickets;

	public PaymentDetailResponse(Payment payment, TicketDetailsResponse ticketDetails) {
		this(
			payment.getId(),
			payment.getMovieTitle(),
			payment.getPaymentNumber(),
			payment.getTotalPrice(),
			payment.getCreatedAt(),
			ticketDetails.getTicketDetails()
		);
	}

}
