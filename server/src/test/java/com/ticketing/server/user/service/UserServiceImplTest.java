package com.ticketing.server.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.service.dto.DeleteUser;
import com.ticketing.server.user.service.dto.DeleteUserTest;
import com.ticketing.server.user.service.dto.SignUp;
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
	SignUp signUp;
	DeleteUser deleteUser;

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userService;

	@BeforeEach
	void init() {
		signUp = new SignUp("유저", "ticketing@gmail.com", "123456", "010-1234-5678");
		user = new User("유저", "ticketing@gmail.com", "123456", UserGrade.GUEST, "010-1234-5678");
		deleteUser = new DeleteUser("ticketing@gmail.com", "123456", DeleteUserTest.CUSTOM_PASSWORD_ENCODER);
	}

	@Test
	@DisplayName("이미 동일한 이메일이 있을 경우")
	void duplicateEmailException() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		Optional<User> user = userService.register(signUp);

		// then
		assertThat(user).isEmpty();
	}

	@Test
	@DisplayName("회원가입 성공했을 경우")
	void UserServiceImplTest() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.empty());
		when(userRepository.save(any())).thenReturn(user);

		// when
		Optional<User> user = userService.register(signUp);

		// then
		assertThat(user).isPresent();
	}

	@Test
	@DisplayName("회원탈퇴 시 이메일이 존재하지 않을 경우")
	void deleteFail() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		Optional<User> user = userService.delete(deleteUser);

		// then
		assertThat(user).isEmpty();
	}

	@Test
	@DisplayName("회원탈퇴 성공했을 경우")
	void deleteSuccess() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		Optional<User> user = userService.delete(deleteUser);

		// then
		assertThat(user).isPresent();
	}

}