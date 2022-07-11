package com.ticketing.server.payment.service;

import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.dto.SimplePaymentDTO;
import com.ticketing.server.payment.service.dto.SimplePaymentsDTO;
import com.ticketing.server.payment.service.interfaces.PaymentService;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	@Override
	public SimplePaymentsDTO findSimplePayments(@NotNull Long userAlternateId) {
		return paymentRepository.findByUserAlternateId(userAlternateId)
			.stream()
			.map(SimplePaymentDTO::new)
			.collect(Collectors
				.collectingAndThen(
					Collectors.toList()
					, payments -> new SimplePaymentsDTO(userAlternateId, payments)
				)
			);
	}

}
