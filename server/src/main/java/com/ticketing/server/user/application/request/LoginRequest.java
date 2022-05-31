package com.ticketing.server.user.application.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class LoginRequest {

	public LoginRequest() {
	}

	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
