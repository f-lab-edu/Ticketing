package com.ticketing.server.payment.service.dto;

import com.ticketing.server.global.redis.PaymentCache;
import com.ticketing.server.payment.application.response.PaymentCancelResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentCancelDTO {

	private final String email;
	private final String movieTitle;
	private final String tid;
	private final List<Long> ticketIds;

	public PaymentCancelDTO(PaymentCache paymentCache) {
		this(
			paymentCache.getEmail(),
			paymentCache.getMovieTitle(),
			paymentCache.getTid(),
			paymentCache.getTicketIds()
		);
	}

	public PaymentCancelResponse toResponse() {
		return new PaymentCancelResponse(email, movieTitle, tid, ticketIds);
	}

}
