package com.ticketing.server.user.application.response;

import com.ticketing.server.user.domain.User;

public class UserChangePasswordResponse {

	public static SignUpResponse of(User user) {
		return new SignUpResponse(user.getName(), user.getEmail());
	}

	public UserChangePasswordResponse(String name, String email) {
		this.name = name;
		this.email = email;
	}

	private String name;

	private String email;

}
