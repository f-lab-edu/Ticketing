package com.ticketing.server.user.api.impl;

import com.ticketing.server.payment.service.PaymentServiceImpl;
import com.ticketing.server.user.api.PaymentClient;
import com.ticketing.server.user.api.dto.request.SimplePaymentsRequest;
import com.ticketing.server.user.api.dto.response.SimplePaymentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClientImpl implements PaymentClient {

	private final PaymentServiceImpl paymentService;

	@Override
	public SimplePaymentsResponse getSimplePayments(SimplePaymentsRequest request) {
		return paymentService.findSimplePayments(request.getUserId());
	}

}
