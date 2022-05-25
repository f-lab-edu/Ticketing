package com.ticketing.server.user.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ticketing.server.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignUpDtoTest {

	@Test
	@DisplayName("toUser 메소드로 User 객체 생성")
	void toUser() {
		// given
		SignUpDTO signUp = new SignUpDTO("유저1", "ticketing@gmail.com", "123456", "010-1234-5678");

		// when
		User user = signUp.toUser();

		// then
		assertThat(user).isInstanceOf(User.class);
	}

}