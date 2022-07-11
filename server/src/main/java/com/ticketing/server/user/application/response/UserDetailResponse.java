package com.ticketing.server.user.application.response;

import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailResponse {

	private final String name;
	private final String email;
	private final UserGrade grade;
	private final String phone;

	public UserDetailResponse(UserDetailDTO userDetailDto) {
		this(
			userDetailDto.getName(),
			userDetailDto.getEmail(),
			userDetailDto.getGrade(),
			userDetailDto.getPhone()
		);
	}

}
