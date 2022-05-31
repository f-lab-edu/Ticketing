package com.ticketing.server.user.application.request;

import com.ticketing.server.global.validator.constraints.Phone;
import com.ticketing.server.user.service.dto.SignUpDTO;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class SignUpRequest {

	public SignUpRequest(String name, String email, String password, String phone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	@NotEmpty(message = "{validation.not.empty.name}")
	private String name;

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	@NotEmpty(message = "{validation.not.empty.phone}")
	@Phone
	private String phone;

	public SignUpDTO toSignUpDto(PasswordEncoder passwordEncoder) {
		return new SignUpDTO(name, email, getEncodePassword(passwordEncoder), phone);
	}

	private String getEncodePassword(PasswordEncoder passwordEncoder) {
		return passwordEncoder.encode(password);
	}

}
