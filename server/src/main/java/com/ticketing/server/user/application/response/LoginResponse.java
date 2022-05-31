package com.ticketing.server.user.application.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {

	public LoginResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	private String accessToken;

}
