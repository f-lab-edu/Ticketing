package com.ticketing.server.user.service.dto;

import com.ticketing.server.user.application.response.UserDetailResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailDTO {

	private final Long alternateId;
	private final String name;
	private final String email;
	private final UserGrade grade;
	private final String phone;

	public UserDetailDTO(User user) {
		this(
			user.getAlternateId(),
			user.getName(),
			user.getEmail(),
			user.getGrade(),
			user.getPhone()
		);
	}

	public UserDetailResponse toResponse() {
		return new UserDetailResponse(name, email, grade, phone);
	}

}
