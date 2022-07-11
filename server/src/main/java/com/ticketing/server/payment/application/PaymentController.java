package com.ticketing.server.payment.application;

import com.ticketing.server.payment.application.response.PaymentDetailResponse;
import com.ticketing.server.payment.service.dto.SimplePaymentsDTO;
import com.ticketing.server.payment.service.interfaces.PaymentApisService;
import com.ticketing.server.payment.service.interfaces.PaymentService;
import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.user.domain.UserGrade;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Validated
public class PaymentController {

	private final PaymentApisService paymentApisService;
	private final PaymentService paymentService;

	@GetMapping
	@Secured(UserGrade.ROLES.USER)
	public ResponseEntity<SimplePaymentsResponse> simplePayments(@NotNull Long userAlternateId) {
		SimplePaymentsDTO simplePayments = paymentService.findSimplePayments(userAlternateId);
		return ResponseEntity.status(HttpStatus.OK).body(simplePayments.toResponse());
	}

	@GetMapping("/detail")
	@Secured(UserGrade.ROLES.USER)
	public ResponseEntity<PaymentDetailResponse> detail(@NotNull Long paymentId) {
		PaymentDetailResponse paymentDetail = paymentApisService.findPaymentDetail(paymentId);
		return ResponseEntity.status(HttpStatus.OK).body(paymentDetail);
	}

}
