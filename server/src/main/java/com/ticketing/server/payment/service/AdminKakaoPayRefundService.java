package com.ticketing.server.payment.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.service.dto.TicketsRefundResponse;
import com.ticketing.server.payment.api.KakaoPayClient;
import com.ticketing.server.payment.api.KakaoPayProperties;
import com.ticketing.server.payment.api.MovieClient;
import com.ticketing.server.payment.api.impl.TicketsRefundRequest;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.interfaces.AbstractKakaoPayRefund;
import org.springframework.stereotype.Service;

@Service
public class AdminKakaoPayRefundService extends AbstractKakaoPayRefund {

	private final PaymentRepository paymentRepository;
	private final MovieClient movieClient;

	public AdminKakaoPayRefundService(KakaoPayClient kakaoPayClient, KakaoPayProperties kakaoPayProperties, PaymentRepository paymentRepository, MovieClient movieClient) {
		super(kakaoPayClient, kakaoPayProperties);
		this.paymentRepository = paymentRepository;
		this.movieClient = movieClient;
	}

	@Override
	protected Payment getPayment(Long paymentId) {
		return paymentRepository.findById(paymentId)
			.orElseThrow(ErrorCode::throwPaymentIdNotFound);
	}

	@Override
	protected TicketsRefundResponse movieAndPaymentRefund(Payment payment) {
		TicketsRefundResponse refundResponse = movieClient.ticketRefund(new TicketsRefundRequest(payment.getId()));
		payment.refund();

		return refundResponse;
	}

}
