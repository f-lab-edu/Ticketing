package com.ticketing.server.user.service.dto;

import com.ticketing.server.user.application.response.SignUpResponse;
import com.ticketing.server.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatedUserDTO {

	private final String name;
	private final String email;

	public CreatedUserDTO(User user) {
		this(
			user.getName(),
			user.getEmail()
		);
	}

	public SignUpResponse toResponse() {
		return new SignUpResponse(name, email);
	}

}
