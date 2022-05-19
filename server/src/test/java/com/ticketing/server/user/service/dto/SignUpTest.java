package com.ticketing.server.user.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ticketing.server.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class SignUpTest {

	@Test
	@DisplayName("toUser 메소드로 User 객체 생성")
	void toUser() {
		// given
		SignUp signUp = new SignUp("유저1", "ticketing@gmail.com", "123456", "010-1234-5678");

		// when
		User user = signUp.toUser(new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return "encoder" + rawPassword;
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return false;
			}
		});

		// then
		assertThat(user).isInstanceOf(User.class);
	}

}