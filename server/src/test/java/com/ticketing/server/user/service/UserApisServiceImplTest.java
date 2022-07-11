package com.ticketing.server.user.service;

import static com.ticketing.server.payment.domain.PaymentStatus.COMPLETED;
import static com.ticketing.server.payment.domain.PaymentType.KAKAO_PAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.payment.service.dto.CreatePaymentDTO;
import com.ticketing.server.payment.service.dto.SimplePaymentDTO;
import com.ticketing.server.user.api.PaymentClient;
import com.ticketing.server.user.application.response.PaymentsResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
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
		PaymentsResponse paymentDetails = userApisService.findPaymentsByEmail("ticketing@gmail.com");

		// then
		assertAll(
			() -> assertThat(paymentDetails.getEmail()).isEqualTo("ticketing@gmail.com")
			, () -> assertThat(paymentDetails.getPayments()).hasSize(0)
		);
	}

	@Test
	@DisplayName("회원 결제목록 조회")
	void findPaymentsByEmailSuccess() {
		// given
		List<SimplePaymentDTO> payments = Arrays.asList(
			new SimplePaymentDTO(
				new CreatePaymentDTO(1L, "범죄도시2", KAKAO_PAY, COMPLETED, "004-323-77542", 15_000).toEntity()),
			new SimplePaymentDTO(
				new CreatePaymentDTO(1L, "토르", KAKAO_PAY, COMPLETED, "004-323-77544", 30_000).toEntity())
		);

		when(userService.findNotDeletedUserByEmail("ticketing@gmail.com")).thenReturn(user);
		when(paymentClient.getPayments(any())).thenReturn(new SimplePaymentsResponse(1L, payments));

		// when
		PaymentsResponse paymentDetails = userApisService.findPaymentsByEmail("ticketing@gmail.com");

		// then
		assertAll(
			() -> assertThat(paymentDetails.getEmail()).isEqualTo("ticketing@gmail.com")
			, () -> assertThat(paymentDetails.getPayments()).hasSize(2)
		);
	}

}
