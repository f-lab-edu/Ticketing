package com.ticketing.server.user.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

	private final String accessToken;
	private final String refreshToken;
	private final String tokenType;
	private final long expiresIn;

}
