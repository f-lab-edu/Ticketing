package com.ticketing.server.user.application.request;

import com.ticketing.server.global.validator.constraints.Phone;
import com.ticketing.server.user.service.dto.SignUp;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotEmpty(message = "이름은 필수 입니다.")
	private String name;

	@NotEmpty(message = "이메일은 필수 입니다.")
	@Email(message = "이메일이 올바르지 않습니다.")
	private String email;

	@NotEmpty(message = "패스워드는 필수 입니다.")
	private String password;

	@NotEmpty(message = "휴대번호는 필수 입니다.")
	@Phone(message = "휴대번호가 올바르지 않습니다.")
	private String phone;

	public SignUp toSignUp() {
		return new SignUp(name, email, password, phone);
	}
}
