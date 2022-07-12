package com.ticketing.server.user.service.dto;

import com.ticketing.server.user.application.response.UserDeleteResponse;
import com.ticketing.server.user.domain.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeletedUserDTO {

	private final String name;
	private final String email;
	private final LocalDateTime deletedAt;

	public DeletedUserDTO(User user) {
		this(user.getName(), user.getEmail(), user.getDeletedAt());
	}

	public UserDeleteResponse toResponse() {
		return new UserDeleteResponse(name, email, deletedAt);
	}

}
