package com.ticketing.server.user.application.request;

import com.ticketing.server.user.domain.UserGrade;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserChangeGradeRequest {

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotNull(message = "{validation.not.null.grade}")
	private UserGrade afterGrade;

}
