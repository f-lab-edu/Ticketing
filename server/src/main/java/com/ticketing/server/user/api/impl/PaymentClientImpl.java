package com.ticketing.server.user.api.impl;

import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.payment.service.dto.SimplePaymentsDTO;
import com.ticketing.server.payment.service.interfaces.PaymentService;
import com.ticketing.server.user.api.PaymentClient;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentClientImpl implements PaymentClient {

	private final PaymentService paymentService;

	@Override
	public SimplePaymentsResponse getPayments(@NotNull Long alternateId) {
		SimplePaymentsDTO payments = paymentService.findSimplePayments(alternateId);
		return payments.toResponse();
	}

}
