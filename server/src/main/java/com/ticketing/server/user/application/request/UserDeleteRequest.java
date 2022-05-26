package com.ticketing.server.user.application.request;

import com.ticketing.server.user.service.dto.DeleteUserDTO;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class UserDeleteRequest {

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	public DeleteUserDTO toDeleteUserDto(PasswordEncoder passwordEncoder) {
		return new DeleteUserDTO(email, password, passwordEncoder);
	}

}
