package com.ticketing.server.user.application.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogoutResponse {

	private String email;

	public static LogoutResponse from(String email) {
		return new LogoutResponse(email);
	}

}
