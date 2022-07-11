package com.ticketing.server.payment.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.payment.api.MovieClient;
import com.ticketing.server.payment.api.UserClient;
import com.ticketing.server.payment.api.dto.response.UserDetailResponse;
import com.ticketing.server.payment.application.response.PaymentDetailResponse;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.interfaces.PaymentApisService;
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
public class PaymentApisServiceImpl implements PaymentApisService {

	private final PaymentRepository paymentRepository;
	private final MovieClient movieClient;
	private final UserClient userClient;

	@Override
	public PaymentDetailResponse findPaymentDetail(@NotNull Long paymentId) {
		Payment payment = paymentRepository.findById(paymentId)
			.orElseThrow(ErrorCode::throwPaymentIdNotFound);

		UserDetailResponse userDetail = userClient.detail();
		if (!userDetail.hasUserAlternateId(payment)) {
			throw ErrorCode.throwValidUserId();
		}

		TicketDetailsResponse tickets = movieClient.getTicketsByPaymentId(payment.getId());
		return new PaymentDetailResponse(payment, tickets);
	}

}
