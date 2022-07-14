package com.ticketing.server.payment.service;

import static com.ticketing.server.payment.domain.PaymentStatus.SOLD;
import static com.ticketing.server.payment.domain.PaymentType.KAKAO_PAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.redis.PaymentCache;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.dto.SimplePaymentsDTO;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

	@Mock
	PaymentRepository paymentRepository;


	@InjectMocks
	PaymentServiceImpl paymentService;

	@Test
	@DisplayName("유저 대체키로 결제내역이 없을 경우")
	void findSimplePaymentsZero() {
		// given
		when(paymentRepository.findByUserAlternateId(2L)).thenReturn(Collections.emptyList());

		// when
		SimplePaymentsDTO simplePayments = paymentService.findSimplePayments(2L);

		// then
		assertThat(simplePayments.getPayments()).isEmpty();
	}

	@ParameterizedTest
	@DisplayName("유저 대체키로 간소 결제내역 조회 성공")
	@ValueSource(longs = {1L, 2L, 100L, 154L})
	void findSimplePaymentsSuccess(Long userAlternateId) {
		// given
		List<Payment> payments = Arrays.asList(
			new Payment(
				new PaymentCache(
					"",
					"범죄도시2",
					"T2d03c9130bf237a97001",
					List.of(3L),
					userAlternateId,
					1241242343245L
				),
				KAKAO_PAY,
				SOLD,
				15_000
			),
			new Payment(
				new PaymentCache(
					"",
					"토르",
					"T2d03c9130bf237a97002",
					List.of(3L),
					userAlternateId,
					12412343212445L
				),
				KAKAO_PAY,
				SOLD,
				30_000
			)
		);
		when(paymentRepository.findByUserAlternateId(userAlternateId)).thenReturn(payments);

		// when
		SimplePaymentsDTO simplePayments = paymentService.findSimplePayments(userAlternateId);

		// then
		assertThat(simplePayments.getPayments()).hasSize(2);
	}

}
