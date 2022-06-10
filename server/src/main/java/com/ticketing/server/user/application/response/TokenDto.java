package com.ticketing.server.user.application.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenDto {

	private final String accessToken;
	private final String refreshToken;
	private final String tokenType;
	private final long expiresIn;

	public static TokenDto of(String accessToken, String refreshToken, String tokenType, long expiresIn) {
		return new TokenDto(accessToken, refreshToken, tokenType, expiresIn);
	}

}
