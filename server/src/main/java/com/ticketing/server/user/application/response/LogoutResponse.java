package com.ticketing.server.user.application.response;

import com.ticketing.server.global.redis.RefreshToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogoutResponse {

	private Long refreshTokenId;
	private String email;
	private String refreshToken;

	private LogoutResponse(String email) {
		this.email = email;
	}

	public static LogoutResponse from(String email) {
		return new LogoutResponse(email);
	}

	public static LogoutResponse from(RefreshToken refreshToken) {
		return new LogoutResponse(refreshToken.getId(), refreshToken.getEmail(), refreshToken.getToken());
	}

}
