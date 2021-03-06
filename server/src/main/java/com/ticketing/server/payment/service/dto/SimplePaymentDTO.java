package com.ticketing.server.payment.service.dto;

import com.ticketing.server.payment.domain.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePaymentDTO {

	private final Long paymentId;
	private final String movieTitle;
	private final String paymentNumber;
	private final Integer totalPrice;

	public SimplePaymentDTO(Payment payment) {
		this(
			payment.getId(),
			payment.getMovieTitle(),
			payment.getPaymentNumber(),
			payment.getTotalPrice()
		);
	}
}
