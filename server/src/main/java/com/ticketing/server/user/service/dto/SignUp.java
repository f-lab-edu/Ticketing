package com.ticketing.server.user.service.dto;

import com.ticketing.server.global.validator.constraints.Phone;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignUp {

	@NotEmpty(message = "{validation.not.empty.name}")
	private String name;

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	@NotEmpty(message = "{validation.not.empty.phone}")
	@Phone(message = "{validation.phone}")
	private String phone;

	public SignUp(String name, String email, String password, String phone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(this.name
			, this.email
			, getEncodePassword(passwordEncoder)
			, UserGrade.GUEST
			, this.phone);
	}

	public String getEncodePassword(PasswordEncoder passwordEncoder) {
		return passwordEncoder.encode(password);
	}

}
