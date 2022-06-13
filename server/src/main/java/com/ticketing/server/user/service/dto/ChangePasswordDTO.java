package com.ticketing.server.user.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ChangePasswordDTO implements PasswordMatches {

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.oldpassword}")
	private String oldPassword;

	@NotEmpty(message = "{validation.not.empty.newpassword}")
	private String newPassword;

	private PasswordEncoder passwordEncoder;

	public ChangePasswordDTO(String email, String oldPassword, String newPassword, PasswordEncoder passwordEncoder) {
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.passwordEncoder = passwordEncoder;
	}

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
