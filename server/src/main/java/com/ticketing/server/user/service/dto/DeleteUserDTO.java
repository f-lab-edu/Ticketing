package com.ticketing.server.user.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class DeleteUserDTO implements PasswordMatches {

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private final String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private final String inputPassword;

	private final PasswordEncoder passwordEncoder;

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
