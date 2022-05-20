package com.ticketing.server.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.repository.UserRepository;
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

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userService;

	@BeforeEach
	void init() {

		signUp = new SignUp("유저", "ticketing@gmail.com", "123456", "010-1234-5678");
		user = new User("유저", "ticketing@gmail.com", "123456", UserGrade.GUEST, "010-1234-5678");
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

}
