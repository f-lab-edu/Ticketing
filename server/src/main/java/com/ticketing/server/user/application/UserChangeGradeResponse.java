package com.ticketing.server.user.application;

import com.ticketing.server.user.domain.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserChangeGradeResponse {

	private String email;

	private UserGrade beforeGrade;

	private UserGrade afterGrade;

}
