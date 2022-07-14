package com.ticketing.server.payment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

	SOLD("판매"),
	REFUNDED("환불");

	private final String statusName;

}
