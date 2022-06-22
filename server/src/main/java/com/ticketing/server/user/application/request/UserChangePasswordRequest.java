package com.ticketing.server.user.application.request;

import com.ticketing.server.global.validator.constraints.FieldsValueNotMatch;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldsValueNotMatch(
	field = "oldPassword",
	fieldMatch = "newPassword",
	message = "{validation.password.not.change}"
)
public class UserChangePasswordRequest {

	@NotEmpty(message = "{validation.not.empty.oldpassword}")
	private String oldPassword;

	@NotEmpty(message = "{validation.not.empty.newpassword}")
	private String newPassword;

	public ChangePasswordDTO toChangePasswordDto(String email, PasswordEncoder passwordEncoder) {
		return new ChangePasswordDTO(email, oldPassword, newPassword, passwordEncoder);
	}

}
