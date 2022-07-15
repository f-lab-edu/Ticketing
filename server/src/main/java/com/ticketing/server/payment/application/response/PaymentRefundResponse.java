package com.ticketing.server.payment.application.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRefundResponse {

	private Long paymentId;
	private List<Long> ticketIds;
	private String movieTitle;
	private Integer cancelAmount;
	private LocalDateTime canceledAt;

}
