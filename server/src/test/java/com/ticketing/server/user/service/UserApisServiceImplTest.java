package com.ticketing.server.user.service;

import static com.ticketing.server.payment.domain.PaymentStatus.SOLD;
import static com.ticketing.server.payment.domain.PaymentType.KAKAO_PAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.redis.PaymentCache;
import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.service.dto.SimplePaymentDTO;
import com.ticketing.server.user.api.PaymentClient;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.service.dto.PaymentsDTO;
import com.ticketing.server.user.service.interfaces.UserService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserApisServiceImplTest {

	User user;

	@BeforeEach
	void init() {
		user = new User(111L, "유저", "ticketing@gmail.com", "123456", UserGrade.USER, "010-1234-5678");
	}

	@Mock
	UserService userService;

	@Mock
	PaymentClient paymentClient;

	@InjectMocks
	UserApisServiceImpl userApisService;

	@Test
	@DisplayName("회원 결제 목록 조회 시 결제목록이 없을 경우")
	void findPaymentsByEmailSuccess_paymentEmpty() {
		// given
		when(userService.findNotDeletedUserByEmail("ticketing@gmail.com")).thenReturn(user);
		when(paymentClient.getPayments(any())).thenReturn(new SimplePaymentsResponse(1L, Collections.emptyList()));

		// when
		PaymentsDTO paymentsDto = userApisService.findPaymentsByEmail("ticketing@gmail.com");

		// then
		assertAll(
			() -> assertThat(paymentsDto.getEmail()).isEqualTo("ticketing@gmail.com")
			, () -> assertThat(paymentsDto.getPayments()).isEmpty()
		);
	}

	@Test
	@DisplayName("회원 결제목록 조회")
	void findPaymentsByEmailSuccess() {
		// given
		List<SimplePaymentDTO> payments = Arrays.asList(
			new SimplePaymentDTO(
				new Payment(
					new PaymentCache(
						user.getEmail(),
						"범죄도시2",
						"T2d03c9130bf237a9700",
						List.of(1L, 2L),
						user.getAlternateId(),
						124124231513245L,
						15_000
					),
					KAKAO_PAY,
					SOLD,
					15_000
				)
			),
			new SimplePaymentDTO(
				new Payment(
					new PaymentCache(
						user.getEmail(),
						"범죄도시2",
						"T2d03c9130bf237a97001",
						List.of(3L),
						user.getAlternateId(),
						1241242343245L,
						15_000
					),
					KAKAO_PAY,
					SOLD,
					15_000
				)
			)
		);

		when(userService.findNotDeletedUserByEmail("ticketing@gmail.com")).thenReturn(user);
		when(paymentClient.getPayments(any())).thenReturn(new SimplePaymentsResponse(1L, payments));

		// when
		PaymentsDTO patmentsDto = userApisService.findPaymentsByEmail("ticketing@gmail.com");

		// then
		assertAll(
			() -> assertThat(patmentsDto.getEmail()).isEqualTo("ticketing@gmail.com")
			, () -> assertThat(patmentsDto.getPayments()).hasSize(2)
		);
	}

}
