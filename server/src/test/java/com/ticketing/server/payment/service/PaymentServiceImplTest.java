package com.ticketing.server.payment.service;

import static com.ticketing.server.payment.domain.PaymentStatus.COMPLETED;
import static com.ticketing.server.payment.domain.PaymentType.KAKAO_PAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.payment.service.dto.CreatePaymentDTO;
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
			new CreatePaymentDTO(userAlternateId, "범죄도시2", KAKAO_PAY, COMPLETED, "004-323-77542", 15_000).toEntity(),
			new CreatePaymentDTO(userAlternateId, "토르", KAKAO_PAY, COMPLETED, "004-323-77544", 30_000).toEntity()
		);
		when(paymentRepository.findByUserAlternateId(userAlternateId)).thenReturn(payments);

		// when
		SimplePaymentsDTO simplePayments = paymentService.findSimplePayments(userAlternateId);

		// then
		assertThat(simplePayments.getPayments()).hasSize(2);
	}

}
