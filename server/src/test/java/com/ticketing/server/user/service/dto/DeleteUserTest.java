package com.ticketing.server.user.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DeleteUserTest {

	public static PasswordEncoder CUSTOM_PASSWORD_ENCODER = new CustomPasswordEncoder();

	public static class CustomPasswordEncoder implements PasswordEncoder {

		@Override
		public String encode(CharSequence rawPassword) {
			return null;
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			return rawPassword.toString().equals(encodedPassword);
		}
	}

	@Test
	@DisplayName("CustomPasswordEncoder matches 테스트")
	void customPasswordEncoderMatches() {
		// given
		DeleteUserDTO user = new DeleteUserDTO("ticketing@gmail.com", "123456", CUSTOM_PASSWORD_ENCODER);

		// when
		// then
		assertThat(user.passwordMatches("123456")).isTrue();
	}

}
