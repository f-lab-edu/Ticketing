package com.ticketing.server.user.application.response;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailResponse {

	private String name;
	private String email;
	private UserGrade grade;
	private String phone;

	public static UserDetailResponse from(User user) {
		return new UserDetailResponse(user.getName(), user.getEmail(), user.getGrade(), user.getPhone());
	}

}
