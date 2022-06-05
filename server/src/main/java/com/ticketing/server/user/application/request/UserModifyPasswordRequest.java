package com.ticketing.server.user.application.request;

import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserModifyPasswordRequest {

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.oldpassword}")
	private String oldPassword;

	@NotEmpty(message = "{validation.not.empty.newpassword}")
	private String newPassword;

	public ChangePasswordDTO toChangePasswordDto(PasswordEncoder passwordEncoder) {
		return new ChangePasswordDTO(email, oldPassword, newPassword, passwordEncoder);
	}

	public boolean oldEqualNew() {
		return oldPassword.equals(newPassword);
	}

}
