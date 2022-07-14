package com.ticketing.server.payment.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCompleteResponse {

	private final String email;
	private final String tid;
	private final String movieTitle;
	private final Integer quantity;
	private final Integer totalAmount;

}
