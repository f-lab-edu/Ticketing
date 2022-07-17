package com.ticketing.server.payment.application;

import static com.ticketing.server.user.domain.UserGrade.ROLES.STAFF;
import static com.ticketing.server.user.domain.UserGrade.ROLES.USER;

import com.ticketing.server.payment.application.request.PaymentReadyRequest;
import com.ticketing.server.payment.application.request.PaymentRefundRequest;
import com.ticketing.server.payment.application.response.PaymentCancelResponse;
import com.ticketing.server.payment.application.response.PaymentCompleteResponse;
import com.ticketing.server.payment.application.response.PaymentDetailResponse;
import com.ticketing.server.payment.application.response.PaymentRefundResponse;
import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.payment.service.AdminKakaoPayRefundService;
import com.ticketing.server.payment.service.MyKakaoPayRefundService;
import com.ticketing.server.payment.service.dto.PaymentCancelDTO;
import com.ticketing.server.payment.service.dto.PaymentCompleteDTO;
import com.ticketing.server.payment.service.dto.PaymentDetailDTO;
import com.ticketing.server.payment.service.dto.PaymentReadyDTO;
import com.ticketing.server.payment.service.dto.PaymentRefundDTO;
import com.ticketing.server.payment.service.dto.SimplePaymentsDTO;
import com.ticketing.server.payment.service.interfaces.PaymentApisService;
import com.ticketing.server.payment.service.interfaces.PaymentService;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Validated
public class PaymentController {

	private final PaymentApisService paymentApisService;
	private final PaymentService paymentService;
	private final AdminKakaoPayRefundService adminKakaoPayRefundService;
	private final MyKakaoPayRefundService myKakaoPayRefundService;

	@GetMapping
	@Secured(USER)
	public ResponseEntity<SimplePaymentsResponse> simplePayments(@NotNull Long userAlternateId) {
		SimplePaymentsDTO simplePayments = paymentService.findSimplePayments(userAlternateId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(simplePayments.toResponse());
	}

	@GetMapping("/detail")
	@Secured(USER)
	public ResponseEntity<PaymentDetailResponse> detail(@NotNull Long paymentId) {
		PaymentDetailDTO paymentDetail = paymentApisService.findPaymentDetail(paymentId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(paymentDetail.toResponse());
	}

	@PostMapping("/ready")
	@Secured(USER)
	public ResponseEntity<PaymentReadyDTO> ready(@RequestBody @Valid PaymentReadyRequest request) {
		PaymentReadyDTO paymentReadyDto = paymentApisService.ready(request.getTicketIds());

		return ResponseEntity.status(HttpStatus.OK)
			.body(paymentReadyDto);
	}

	@GetMapping("/complete")
	@Secured(USER)
	public ResponseEntity<PaymentCompleteResponse> complete(
		@AuthenticationPrincipal UserDetails userRequest,
		@RequestParam("pg_token") String pgToken) {
		PaymentCompleteDTO paymentCompleteDto = paymentApisService.complete(userRequest.getUsername(), pgToken);

		return ResponseEntity.status(HttpStatus.OK)
			.body(paymentCompleteDto.toResponse());
	}

	@GetMapping("/cancel")
	@Secured(USER)
	public ResponseEntity<PaymentCancelResponse> cancel(@AuthenticationPrincipal UserDetails userRequest) {
		PaymentCancelDTO paymentCancelDto = paymentApisService.cancel(userRequest.getUsername());

		return ResponseEntity.status(HttpStatus.OK)
			.body(paymentCancelDto.toResponse());
	}

	@PostMapping("/refund")
	@Secured(USER)
	public ResponseEntity<PaymentRefundResponse> refund(@RequestBody @Valid PaymentRefundRequest request) {
		PaymentRefundDTO paymentRefundDto = myKakaoPayRefundService.refund(request.getPaymentId());

		return ResponseEntity.status(HttpStatus.OK)
			.body(paymentRefundDto.toResponse());
	}

	@PostMapping("/staff/refund")
	@Secured(STAFF)
	public ResponseEntity<PaymentRefundResponse> adminRefund(@RequestBody @Valid PaymentRefundRequest request) {
		PaymentRefundDTO paymentRefundDto = adminKakaoPayRefundService.refund(request.getPaymentId());

		return ResponseEntity.status(HttpStatus.OK)
			.body(paymentRefundDto.toResponse());
	}

}
