package com.ticketing.server.payment.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.service.dto.TicketsRefundResponse;
import com.ticketing.server.payment.api.KakaoPayClient;
import com.ticketing.server.payment.api.KakaoPayProperties;
import com.ticketing.server.payment.api.MovieClient;
import com.ticketing.server.payment.api.UserClient;
import com.ticketing.server.payment.api.dto.response.UserDetailResponse;
import com.ticketing.server.payment.api.impl.TicketsRefundRequest;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.interfaces.AbstractKakaoPayRefund;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class MyKakaoPayRefundService extends AbstractKakaoPayRefund {

	private final PaymentRepository paymentRepository;
	private final MovieClient movieClient;
	private final UserClient userClient;

	public MyKakaoPayRefundService(KakaoPayClient kakaoPayClient, KakaoPayProperties kakaoPayProperties, PaymentRepository paymentRepository,
		MovieClient movieClient, UserClient userClient) {
		super(kakaoPayClient, kakaoPayProperties);
		this.paymentRepository = paymentRepository;
		this.movieClient = movieClient;
		this.userClient = userClient;
	}

	@Override
	protected Payment getPayment(Long paymentId) {
		UserDetailResponse userDetail = userClient.detail();
		Payment payment = paymentRepository.findById(paymentId)
			.orElseThrow(ErrorCode::throwPaymentIdNotFound);

		if (userDetail.hasUserAlternateId(payment)) {
			throw ErrorCode.throwValidUserId();
		}

		return payment;
	}

	@Override
	protected TicketsRefundResponse movieAndPaymentRefund(Payment payment) {
		LocalDateTime now = LocalDateTime.now();
		TicketsRefundResponse refundResponse = movieClient.ticketRefundByDateTime(new TicketsRefundRequest(payment.getId()), now);
		payment.refund();

		return refundResponse;
	}

}
