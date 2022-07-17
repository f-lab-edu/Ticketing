package com.ticketing.server.payment.api.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketsRefundRequest {

	private final Long paymentId;

}
