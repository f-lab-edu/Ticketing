package com.ticketing.server.user.application.request;

import com.ticketing.server.user.service.dto.ChangePassword;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordModifyRequest {

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.oldpassword}")
	private String oldPassword;

	@NotEmpty(message = "{validation.not.empty.newpassword}")
	private String newPassword;

	public ChangePassword toChangePassword(PasswordEncoder passwordEncoder) {
		return new ChangePassword(email, oldPassword, newPassword, passwordEncoder);
	}

	public boolean oldEqualNew() {
		return oldPassword.equals(newPassword);
	}
}
