package com.ticketing.server.payment.service;

import static com.ticketing.server.movie.domain.TicketTest.setupTickets;
import static com.ticketing.server.payment.domain.PaymentTest.PAYMENT_USER_1;
import static com.ticketing.server.user.domain.UserTest.provideCorrectUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.movie.application.response.TicketDetailsResponse;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.payment.api.MovieClient;
import com.ticketing.server.payment.api.UserClient;
import com.ticketing.server.payment.api.dto.response.UserDetailResponse;
import com.ticketing.server.payment.application.response.PaymentDetailResponse;
import com.ticketing.server.payment.service.dto.PaymentDetailDTO;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentApisServiceImplTest {

	private Map<String, User> users;
	private List<Ticket> tickets;

	@Mock
	UserClient userClient;

	@Mock
	MovieClient movieClient;

	@Mock
	PaymentRepository paymentRepository;


	@InjectMocks
	PaymentApisServiceImpl paymentApisService;

	@BeforeEach
	void init() {
		users = provideCorrectUsers().collect(Collectors.toMap(User::getEmail, user -> user));
		tickets = setupTickets();
	}

	@Test
	@DisplayName("상세 결제정보 조회 실패 - paymentId 가 존재하지 않을 경우")
	void findPaymentDetailNotPaymentIdFail() {
		// given
		when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> paymentApisService.findPaymentDetail(1L))
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.PAYMENT_ID_NOT_FOUND);
	}

	@Test
	@DisplayName("상세 결제정보 조회 실패 - 결제UserId와 JWT 로그인 userId 불일치")
	void findPaymentDetailUserIdFail() {
		// given
		when(paymentRepository.findById(1L)).thenReturn(Optional.of(PAYMENT_USER_1));

		UserDetailDTO userDetail = new UserDetailDTO(users.get("ticketing2@gmail.com"));
		when(userClient.detail()).thenReturn(new UserDetailResponse(userDetail));

		// when
		// then
		assertThatThrownBy(() -> paymentApisService.findPaymentDetail(1L))
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.VALID_USER_ID);
	}

	@Test
	@DisplayName("상세 결제정보 조회 성공")
	void findPaymentDetailSuccess() {
		// given
		UserDetailDTO userDetail = new UserDetailDTO(users.get("ticketing1@gmail.com"));
		TicketDetailsResponse response = new TicketDetailsResponse(Arrays.asList(
			new TicketDetailDTO(tickets.get(0)),
			new TicketDetailDTO(tickets.get(1))
		));

		when(paymentRepository.findById(1L)).thenReturn(Optional.of(PAYMENT_USER_1));
		when(userClient.detail()).thenReturn(new UserDetailResponse(userDetail));
		when(movieClient.getTicketsByPaymentId(1L)).thenReturn(response);

		// when
		PaymentDetailDTO paymentDetail = paymentApisService.findPaymentDetail(1L);

		// then
		assertAll(
			() -> assertThat(paymentDetail.getPaymentId()).isEqualTo(1L),
			() -> assertThat(paymentDetail.getMovieTitle()).isEqualTo("탑건: 매버릭"),
			() -> assertThat(paymentDetail.getPaymentNumber()).isEqualTo("2022-0710-4142"),
			() -> assertThat(paymentDetail.getTotalPrice()).isEqualTo(30_000),
			() -> assertThat(paymentDetail.getTickets()).hasSize(2)
		);
	}

}
