package com.ticketing.server.user.application.response;

import com.ticketing.server.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDeleteResponse {

	private String name;

	private String email;

	public static UserDeleteResponse from(User user) {
		return new UserDeleteResponse(user.getName(), user.getEmail());
	}

}
