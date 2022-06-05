package com.ticketing.server.user.application.response;

import com.ticketing.server.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserChangePasswordResponse {

	private String name;

	private String email;

	public static UserChangePasswordResponse of(User user) {
		return new UserChangePasswordResponse(user.getName(), user.getEmail());
	}

}
