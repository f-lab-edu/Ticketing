package com.ticketing.server.user.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponse {

	private final String name;
	private final String email;

}
