package com.ticketing.server.user.service.dto;

import com.ticketing.server.global.validator.constraints.Phone;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpDTO {

	@NotEmpty(message = "{validation.not.empty.name}")
	private final String name;

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private final String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private final String password;

	@NotEmpty(message = "{validation.not.empty.phone}")
	@Phone
	private final String phone;

	public User toUser(long alternateId) {
		return new User(alternateId, this.name, this.email, password, UserGrade.USER, this.phone);
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
