package com.ticketing.server.user.service.dto;

import com.ticketing.server.global.redis.RefreshToken;
import com.ticketing.server.user.application.response.LogoutResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteRefreshTokenDTO {

	private final Long refreshTokenId;
	private final String email;
	private final String refreshToken;

	public DeleteRefreshTokenDTO(String email) {
		this(null, email, null);
	}

	public DeleteRefreshTokenDTO(RefreshToken refreshToken) {
		this(refreshToken.getId(), refreshToken.getEmail(), refreshToken.getToken());
	}

	public LogoutResponse toResponse() {
		return new LogoutResponse(refreshTokenId, email, refreshToken);
	}
}
