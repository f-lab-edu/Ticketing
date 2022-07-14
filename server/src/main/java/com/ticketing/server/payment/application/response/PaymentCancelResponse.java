package com.ticketing.server.payment.application.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCancelResponse {

	private final String email;
	private final String movieTitle;
	private final String tid;
	private final List<Long> ticketIds;

}
