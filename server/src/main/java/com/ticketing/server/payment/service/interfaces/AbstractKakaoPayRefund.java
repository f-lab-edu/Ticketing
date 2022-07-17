package com.ticketing.server.payment.service.interfaces;

import com.ticketing.server.movie.service.dto.TicketsRefundResponse;
import com.ticketing.server.payment.api.KakaoPayClient;
import com.ticketing.server.payment.api.KakaoPayProperties;
import com.ticketing.server.payment.api.dto.requset.KakaoPayCancelRequest;
import com.ticketing.server.payment.api.dto.response.KakaoPayCancelResponse;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.service.dto.PaymentRefundDTO;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Validated
public abstract class AbstractKakaoPayRefund {

	private final KakaoPayClient kakaoPayClient;
	private final KakaoPayProperties kakaoPayProperties;

	@Transactional
	public PaymentRefundDTO refund(@NotNull Long paymentId) {
		Payment payment = getPayment(paymentId);

		// 내부 환불
		TicketsRefundResponse refundResponse = movieAndPaymentRefund(payment);

		// 카카오페이 환불
		KakaoPayCancelResponse kakaoPayCancelResponse = kakaoPayClient.cancel(
			kakaoPayProperties.getAuthorization(),
			new KakaoPayCancelRequest(payment.getTid(), payment.getTotalPrice())
		);

		return new PaymentRefundDTO(payment, kakaoPayCancelResponse, refundResponse);
	}

	protected abstract Payment getPayment(Long paymentId);

	protected abstract TicketsRefundResponse movieAndPaymentRefund(Payment payment);

}
