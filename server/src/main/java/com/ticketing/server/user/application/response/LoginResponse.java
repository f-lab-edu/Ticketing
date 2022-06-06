package com.ticketing.server.user.application.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {

	private String accessToken;
	private String refreshToken;

	public static LoginResponse of(String accessToken, String refreshToken) {
		return new LoginResponse(accessToken, refreshToken);
	}

}
