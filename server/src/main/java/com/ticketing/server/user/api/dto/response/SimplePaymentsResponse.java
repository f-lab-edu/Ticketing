package com.ticketing.server.user.api.dto.response;

import com.ticketing.server.payment.service.dto.SimplePaymentDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePaymentsResponse {

	private Long userId;

	private List<SimplePaymentDto> payments;

	public static SimplePaymentsResponse from(Long userId, List<SimplePaymentDto> simplePayments) {
		return new SimplePaymentsResponse(userId, simplePayments);
	}

}
