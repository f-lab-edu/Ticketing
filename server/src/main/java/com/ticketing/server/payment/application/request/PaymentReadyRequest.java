package com.ticketing.server.payment.application.request;

import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import java.util.List;
import lombok.Getter;

@Getter
public class PaymentReadyRequest {

	@NotEmptyCollection
	private List<Long> ticketIds;

}
