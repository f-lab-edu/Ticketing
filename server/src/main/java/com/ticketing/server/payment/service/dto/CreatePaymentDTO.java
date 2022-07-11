package com.ticketing.server.payment.service.dto;

import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.PaymentStatus;
import com.ticketing.server.payment.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePaymentDTO {

	private Long userAlternateId;
	private String movieTitle;
	private PaymentType type;
	private PaymentStatus status;
	private String paymentNumber;
	private Integer totalPrice;

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
