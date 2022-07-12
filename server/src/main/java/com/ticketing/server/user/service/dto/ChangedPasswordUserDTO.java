package com.ticketing.server.user.service.dto;

import com.ticketing.server.user.application.response.UserChangePasswordResponse;
import com.ticketing.server.user.domain.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChangedPasswordUserDTO {

	private final String name;
	private final String email;
	private final LocalDateTime updatedAt;

	public ChangedPasswordUserDTO(User user) {
		this(user.getName(), user.getEmail(), user.getUpdatedAt());
	}

	public UserChangePasswordResponse toResponse() {
		return new UserChangePasswordResponse(name, email, updatedAt);
	}

}
