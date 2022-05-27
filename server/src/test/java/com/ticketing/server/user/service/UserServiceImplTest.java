package com.ticketing.server.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.exception.NotFoundEmailException;
import com.ticketing.server.global.exception.PasswordMismatchException;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserDtoTest;
import com.ticketing.server.user.service.dto.LoginDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
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
			.isInstanceOf(IllegalArgumentException.class);
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
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.delete(deleteUserDto))
			.isInstanceOf(NotFoundEmailException.class);
	}

	@Test
	@DisplayName("회원탈퇴 성공했을 경우")
	void deleteSuccess() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		User user = userService.delete(deleteUserDto);

		// then
		assertThat(user).isNotNull();
	}

	@Test
	@DisplayName("패스워드 변경 시 이메일이 존재하지 않을 경우")
	void changePasswordFail() {
		// given
		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.changePassword(changePasswordDto))
			.isInstanceOf(NotFoundEmailException.class);
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
	@DisplayName("로그인 성공 시")
	void loginSuccess() {
		// given
		LoginDTO loginDTO = new LoginDTO("ticketing@gmail.com", "123456", DeleteUserDtoTest.CUSTOM_PASSWORD_ENCODER);
		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		User user = userService.login(loginDTO);

		// then
		assertThat(user).isInstanceOf(User.class);
	}

	@Test
	@DisplayName("로그인 시도 시 이메일이 없을 경우")
	void loginNotFoundEmail() {
		// given
		LoginDTO loginDTO = new LoginDTO("ticketing1@gmail.com", "123456", DeleteUserDtoTest.CUSTOM_PASSWORD_ENCODER);
		when(userRepository.findByEmailAndIsDeletedFalse(any())).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.login(loginDTO))
			.isInstanceOf(NotFoundEmailException.class);
	}

	@Test
	@DisplayName("로그인 시도 시 패스워드가 일치하지 않을 경우")
	void loginPasswordMatchesFail() {
		// given
		LoginDTO loginDTO = new LoginDTO("ticketing@gmail.com", "1234567", DeleteUserDtoTest.CUSTOM_PASSWORD_ENCODER);
		when(userRepository.findByEmailAndIsDeletedFalse("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		// then
		assertThatThrownBy(() -> userService.login(loginDTO))
			.isInstanceOf(PasswordMismatchException.class);
	}

}
