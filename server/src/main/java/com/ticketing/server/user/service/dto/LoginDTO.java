package com.ticketing.server.user.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class LoginDTO implements PasswordMatches {

	public LoginDTO() {
	}

	public LoginDTO(String email, String password, PasswordEncoder passwordEncoder) {
		this.email = email;
		this.password = password;
		this.passwordEncoder = passwordEncoder;
	}

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	private PasswordEncoder passwordEncoder;

	@Override
	public boolean passwordMatches(String encodedPassword) {
		return passwordEncoder.matches(this.password, encodedPassword);
	}

	@Override
	public String toString() {
		return "LoginDTO{" +
			"email='" + email + '\'' +
			'}';
	}

}
