package com.ticketing.server.user.application.request;

import com.ticketing.server.global.validator.constraints.Phone;
import com.ticketing.server.user.service.dto.SignUp;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotEmpty(message = "{validation.not.empty.name}")
	private String name;

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	@NotEmpty(message = "{validation.not.empty.phone}")
	@Phone
	private String phone;

	public SignUp toSignUp() {
		return new SignUp(name, email, password, phone);
	}
}
