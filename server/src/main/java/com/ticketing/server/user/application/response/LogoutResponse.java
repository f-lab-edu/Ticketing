package com.ticketing.server.user.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutResponse {

	private final Long refreshTokenId;
	private final String email;
	private final String refreshToken;

}
