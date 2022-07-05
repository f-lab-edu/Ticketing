package com.ticketing.server.payment.service.dto;

import com.ticketing.server.payment.domain.PaymentStatus;
import com.ticketing.server.payment.domain.PaymentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePaymentDto {

	private Long userId;
	private String movieTitle;
	private PaymentType type;
	private PaymentStatus status;
	private String paymentNumber;
	private Integer totalPrice;

	public static CreatePaymentDto of(
		Long userId,
		String movieTitle,
		PaymentType type,
		PaymentStatus status,
		String paymentNumber,
		Integer totalPrice) {
		return new CreatePaymentDto(userId, movieTitle, type, status, paymentNumber, totalPrice);
	}

}
