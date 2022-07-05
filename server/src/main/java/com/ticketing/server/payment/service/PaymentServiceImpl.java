package com.ticketing.server.payment.service;

import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.dto.SimplePaymentDto;
import com.ticketing.server.payment.service.interfaces.PaymentService;
import com.ticketing.server.user.api.dto.response.SimplePaymentsResponse;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	@Override
	public SimplePaymentsResponse findSimplePayments(Long userId) {
		return paymentRepository.findByUserId(userId)
			.stream()
			.map(SimplePaymentDto::from)
			.collect(Collectors.collectingAndThen(Collectors.toList()
				, list -> SimplePaymentsResponse.from(userId, list)));
	}

}
