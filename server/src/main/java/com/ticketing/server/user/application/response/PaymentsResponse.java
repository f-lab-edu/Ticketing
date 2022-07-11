package com.ticketing.server.user.application.response;

import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.payment.service.dto.SimplePaymentDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentsResponse {

	private String email;
	private List<SimplePaymentDTO> payments;

	public PaymentsResponse(String email, SimplePaymentsResponse simplePayments) {
		this(email, simplePayments.getPayments());
	}
	
}
