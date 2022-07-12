package com.ticketing.server.payment.service.dto;

import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.PaymentStatus;
import com.ticketing.server.payment.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePaymentDTO {

	private final Long userAlternateId;
	private final String movieTitle;
	private final PaymentType type;
	private final PaymentStatus status;
	private final String paymentNumber;
	private final Integer totalPrice;

	public Payment toEntity() {
		return new Payment
			(
				this.getUserAlternateId(),
				this.getMovieTitle(),
				this.getType(),
				this.getStatus(),
				this.getPaymentNumber(),
				this.getTotalPrice()
			);
	}

}
