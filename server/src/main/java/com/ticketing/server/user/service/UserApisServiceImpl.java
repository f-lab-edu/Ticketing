package com.ticketing.server.user.service;

import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.user.api.PaymentClient;
import com.ticketing.server.user.application.response.PaymentsResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.interfaces.UserApisService;
import com.ticketing.server.user.service.interfaces.UserService;
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
public class UserApisServiceImpl implements UserApisService {

	private final PaymentClient paymentClient;
	private final UserService userService;

	@Override
	public PaymentsResponse findPaymentsByEmail(@NotNull String email) {
		User user = userService.findNotDeletedUserByEmail(email);
		SimplePaymentsResponse simplePayments = paymentClient.getPayments(user.getAlternateId());

		return new PaymentsResponse(user.getEmail(), simplePayments);
	}

}
