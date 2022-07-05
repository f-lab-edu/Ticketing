package com.ticketing.server.user.application.response;

import com.ticketing.server.payment.service.dto.SimplePaymentDto;
import com.ticketing.server.user.api.dto.response.SimplePaymentsResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePaymentDetailsResponse {

	private String email;
	private List<SimplePaymentDto> payments;

	public static SimplePaymentDetailsResponse of(String email, SimplePaymentsResponse paymentsResponse) {
		return new SimplePaymentDetailsResponse(email, paymentsResponse.getPayments());
	}

}
