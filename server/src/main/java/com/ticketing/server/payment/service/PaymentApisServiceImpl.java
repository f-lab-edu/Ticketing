package com.ticketing.server.payment.service;

import com.ticketing.server.global.dto.SequenceGenerator;
import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.redis.PaymentCache;
import com.ticketing.server.global.redis.PaymentCacheRepository;
import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import com.ticketing.server.movie.application.request.TicketCancelRequest;
import com.ticketing.server.movie.application.request.TicketReservationRequest;
import com.ticketing.server.movie.application.request.TicketSoldRequest;
import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.movie.application.response.TicketReservationResponse;
import com.ticketing.server.payment.api.KakaoPayClient;
import com.ticketing.server.payment.api.KakaoPayProperties;
import com.ticketing.server.payment.api.MovieClient;
import com.ticketing.server.payment.api.UserClient;
import com.ticketing.server.payment.api.dto.requset.KakaoPayApproveRequest;
import com.ticketing.server.payment.api.dto.requset.KakaoPayReadyRequest;
import com.ticketing.server.payment.api.dto.response.KakaoPayApproveResponse;
import com.ticketing.server.payment.api.dto.response.KakaoPayReadyResponse;
import com.ticketing.server.payment.api.dto.response.UserDetailResponse;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.PaymentStatus;
import com.ticketing.server.payment.domain.PaymentType;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.dto.PaymentCancelDTO;
import com.ticketing.server.payment.service.dto.PaymentCompleteDTO;
import com.ticketing.server.payment.service.dto.PaymentDetailDTO;
import com.ticketing.server.payment.service.dto.PaymentReadyDTO;
import com.ticketing.server.payment.service.interfaces.PaymentApisService;
import java.util.List;
import javax.validation.constraints.NotEmpty;
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

	private final KakaoPayProperties kakaoPayProperties;

	private final PaymentRepository paymentRepository;
	private final PaymentCacheRepository paymentCacheRepository;

	private final MovieClient movieClient;
	private final UserClient userClient;
	private final KakaoPayClient kakaoPayClient;

	private final SequenceGenerator sequenceGenerator;

	@Override
	public PaymentDetailDTO findPaymentDetail(@NotNull Long paymentId) {
		Payment payment = paymentRepository.findById(paymentId)
			.orElseThrow(ErrorCode::throwPaymentIdNotFound);

		UserDetailResponse userDetail = userClient.detail();
		if (!userDetail.hasUserAlternateId(payment)) {
			throw ErrorCode.throwValidUserId();
		}

		TicketDetailsResponse tickets = movieClient.getTicketsByPaymentId(payment.getId());
		return new PaymentDetailDTO(payment, tickets);
	}

	@Override
	@Transactional
	public PaymentReadyDTO ready(@NotEmptyCollection List<Long> ticketIds) {
		// User 정보 조회 - user 의 대체 키가 필요함.
		UserDetailResponse userResponse = userClient.detail();
		Long userAlternateId = userResponse.getUserAlternateId();

		// 다른 결제중인 데이터가 있다면, 예외처리
		paymentCacheRepository.findByEmail(userResponse.getEmail())
			.ifPresent(paymentCache -> ErrorCode.throwBadRequestPaymentReady());

		// Ticket 정보 조회 - 영화제목, 티켓가격이 필요함.
		TicketReservationResponse ticketResponse = movieClient.ticketReservation(new TicketReservationRequest(ticketIds));
		String movieTitle = ticketResponse.getMovieTitle();

		// 예매번호 자동생성
		Long paymentNumber = sequenceGenerator.generateId();

		KakaoPayReadyRequest request = new KakaoPayReadyRequest(
			paymentNumber.toString(),
			userAlternateId.toString(),
			movieTitle,
			ticketResponse.getTicketQuantity(),
			ticketResponse.getTotalPrice(),
			kakaoPayProperties.getApprovalUrl(),
			kakaoPayProperties.getCancelUrl(),
			kakaoPayProperties.getFailUrl()
		);
		KakaoPayReadyResponse response = kakaoPayClient.ready(kakaoPayProperties.getAuthorization(), request);

		paymentCacheRepository.save(
			new PaymentCache(
				userResponse.getEmail(),
				movieTitle,
				response.getTid(),
				ticketIds,
				userAlternateId,
				paymentNumber,
				request.getTotalAmount()
			)
		);

		return new PaymentReadyDTO(response);
	}

	@Override
	@Transactional
	public PaymentCompleteDTO complete(@NotEmpty String email, @NotEmpty String pgToken) {
		PaymentCache paymentCache = paymentCacheRepository.findByEmail(email)
			.orElseThrow(ErrorCode::throwBadRequestPaymentComplete);

		Payment payment = new Payment(paymentCache, PaymentType.KAKAO_PAY, PaymentStatus.SOLD, paymentCache.getTotalAmount());
		payment = paymentRepository.save(payment);

		movieClient.ticketSold(new TicketSoldRequest(payment.getId(), paymentCache.getTicketIds()));
		paymentCacheRepository.delete(paymentCache);

		// 카카오페이 결제완료
		String paymentNumberToString = paymentCache.getPaymentNumber().toString();
		String userAlternateIdToString = paymentCache.getUserAlternateId().toString();
		KakaoPayApproveRequest kakaoPayApproveRequest = new KakaoPayApproveRequest(
			paymentCache.getTid(),
			paymentNumberToString,
			userAlternateIdToString,
			pgToken
		);
		KakaoPayApproveResponse kakaoPayApproveResponse = kakaoPayClient.approve(
			kakaoPayProperties.getAuthorization(),
			kakaoPayApproveRequest
		);

		return new PaymentCompleteDTO(email, kakaoPayApproveResponse);
	}

	@Override
	@Transactional
	public PaymentCancelDTO cancel(@NotEmpty String email) {
		PaymentCache paymentCache = paymentCacheRepository.findByEmail(email)
			.orElseThrow(ErrorCode::throwBadRequestPaymentComplete);

		movieClient.ticketCancel(new TicketCancelRequest(paymentCache.getTicketIds()));
		paymentCacheRepository.delete(paymentCache);

		return new PaymentCancelDTO(paymentCache);
	}

}
