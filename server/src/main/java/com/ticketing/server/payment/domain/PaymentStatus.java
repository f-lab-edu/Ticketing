package com.ticketing.server.payment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

	SOLD("ํ๋งค"),
	REFUNDED("ํ๋ถ");

	private final String statusName;

}
