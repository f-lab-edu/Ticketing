package com.ticketing.server.payment.application.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentRefundRequest {

	@NotNull
	private Long paymentId;

}
