package com.ticketing.server.user.service;

import static com.ticketing.server.payment.domain.PaymentStatus.COMPLETED;
import static com.ticketing.server.payment.domain.PaymentType.KAKAO_PAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.service.dto.CreatePaymentDto;
import com.ticketing.server.payment.service.dto.SimplePaymentDto;
import com.ticketing.server.user.api.PaymentClient;
import com.ticketing.server.user.api.dto.request.SimplePaymentsRequest;
import com.ticketing.server.user.api.dto.response.SimplePaymentsResponse;
import com.ticketing.server.user.application.response.SimplePaymentDetailsResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserDtoTest;
import com.ticketing.server.user.service.dto.SignUpDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	User user;
	SignUpDTO signUpDto;
	DeleteUserDTO deleteUserDto;
	ChangePasswordDTO changePasswordDto;

	@Mock
	UserRepository userRepository;

	@Mock
	PaymentClient paymentClient;

	@InjectMocks
	UserServiceImpl userService;

	@BeforeEach
	void init() {
		signUpDto = new SignUpDTO("유저", "ticketing@gmail.com", "123456", "010-1234-5678");
		user = new User("유저", "ticketing@gmail.com", "123456", UserGrade.GUEST, "010-1234-5678");
		deleteUserDto = new DeleteUserDTO("ticketing@gmail.com", "123456", DeleteUserDtoTest.CUSTOM_PASSWORD_ENCODER);
		changePasswordDto = new ChangePasswordDTO("ticketing@gmail.com", "123456", "ticketing1234", DeleteUserDtoTest.CUSTOM_PASSWORD_ENCODER);
	}

	@Test
	@DisplayName("이미 동일한 이메일이 있을 경우")
	void duplicateEmailException() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		// then
		assertThatThrownBy(() -> userService.register(signUpDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("회원가입 성공했을 경우")
	void registerSuccess() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.empty());
		when(userRepository.save(any())).thenReturn(user);

		// when
		User user = userService.register(signUpDto);

		// then
		assertThat(user).isNotNull();
	}

	@Test
	@DisplayName("회원탈퇴 시 이메일이 존재하지 않을 경우")
	void deleteFail() {
		// given
		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.delete(deleteUserDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("회원탈퇴 성공했을 경우")
	void deleteSuccess() {
		// given
		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		User user = userService.delete(deleteUserDto);

		// then
		assertAll(
			() -> assertThat(user.isDeleted()).isTrue(),
			() -> assertThat(user.getDeletedAt()).isNotNull()
		);
	}

	@Test
	@DisplayName("패스워드 변경 시 이메일이 존재하지 않을 경우")
	void changePasswordFail() {
		// given
		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.changePassword(changePasswordDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("패스워드 변경 성공했을 경우")
	void changePasswordSuccess() {
		// given
		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		User user = userService.changePassword(changePasswordDto);

		// then
		assertThat(user).isNotNull();
	}

	@Test
	@DisplayName("회원 결제목록 조회")
	void findSimplePaymentDetailsSuccess() {
	    // given
		List<SimplePaymentDto> paymentDtos = Arrays.asList(
			SimplePaymentDto.from(
				Payment.from(
					CreatePaymentDto.of(1L, "범죄도시2", KAKAO_PAY, COMPLETED, "004-323-77542", 15_000))),
			SimplePaymentDto.from(
				Payment.from(
					CreatePaymentDto.of(1L, "토르", KAKAO_PAY, COMPLETED, "004-323-77544", 30_000)))
		);

		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.of(user));
		when(paymentClient.getSimplePayments(any())).thenReturn(SimplePaymentsResponse.from(1L, paymentDtos));

	    // when
		SimplePaymentDetailsResponse paymentDetails = userService.findSimplePaymentDetails("ticketing@gmail.com");

		// then
		assertAll(
			() -> assertThat(paymentDetails.getEmail()).isEqualTo("ticketing@gmail.com")
			, () -> assertThat(paymentDetails.getPayments()).hasSize(2)
		);
	}

}
