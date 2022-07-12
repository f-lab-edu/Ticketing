package com.ticketing.server.user.service.dto;

import com.ticketing.server.user.application.response.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDTO {

	private final String accessToken;
	private final String refreshToken;
	private final String tokenType;
	private final long expiresIn;

	public TokenResponse toResponse() {
		return new TokenResponse(accessToken, refreshToken, tokenType, expiresIn);
	}

}
