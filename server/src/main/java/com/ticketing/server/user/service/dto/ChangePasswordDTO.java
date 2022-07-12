package com.ticketing.server.user.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class ChangePasswordDTO implements PasswordMatches {

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private final String email;

	@NotEmpty(message = "{validation.not.empty.oldpassword}")
	private final String oldPassword;

	@NotEmpty(message = "{validation.not.empty.newpassword}")
	private final String newPassword;

	private final PasswordEncoder passwordEncoder;

	public String getEmail() {
		return email;
	}

	@Override
	public boolean passwordMatches(String password) {
		return passwordEncoder.matches(oldPassword, password);
	}

	public String getEncodePassword() {
		return passwordEncoder.encode(newPassword);
	}

	@Override
	public String toString() {
		return "ChangePassword{" +
			"email='" + email + '\'' +
			'}';
	}
}
