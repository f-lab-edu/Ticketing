package com.ticketing.server.user.domain;

import com.ticketing.server.user.application.response.UserChangeGradeResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeGradeDTO {

	private final String email;
	private final UserGrade beforeGrade;
	private final UserGrade afterGrade;

	public UserChangeGradeResponse toResponse() {
		return new UserChangeGradeResponse(email, beforeGrade, afterGrade);
	}

}
