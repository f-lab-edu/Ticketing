package com.ticketing.server.user.service.dto;

import com.ticketing.server.global.validator.constraints.Phone;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignUpDTO {

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

	public SignUpDTO(String name, String email, String password, String phone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public User toUser() {
		return new User(this.name, this.email, password, UserGrade.GUEST, this.phone);
	}

	@Override
	public String toString() {
		return "SignUp{" +
			"name='" + name + '\'' +
			", email='" + email + '\'' +
			", phone='" + phone + '\'' +
			'}';
	}

}
