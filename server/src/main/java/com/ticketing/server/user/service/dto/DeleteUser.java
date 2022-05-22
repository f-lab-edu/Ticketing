package com.ticketing.server.user.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DeleteUser implements PasswordMatches {

	public DeleteUser(String email, String inputPassword, PasswordEncoder passwordEncoder) {
		this.email = email;
		this.inputPassword = inputPassword;
		this.passwordEncoder = passwordEncoder;
	}

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private String inputPassword;

	private PasswordEncoder passwordEncoder;

	@Override
	public boolean passwordMatches(String password) {
		return passwordEncoder.matches(this.inputPassword, password);
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "DeleteUser{" +
			"email='" + email + '\'' +
			'}';
	}

}
