package com.ticketing.server.user.application.response;

import com.ticketing.server.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpResponse {

	private String name;

	private String email;

	public static SignUpResponse from(User user) {
		return new SignUpResponse(user.getName(), user.getEmail());
	}

}
