package com.ticketing.server.user.application;

import com.ticketing.server.user.application.request.LoginRequest;
import com.ticketing.server.user.application.response.TokenDto;
import com.ticketing.server.user.service.interfaces.AuthenticationService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

	private final AuthenticationService authenticationService;

	@PostMapping("/token")
	public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		TokenDto tokenDto = authenticationService.generateTokenDto(loginRequest.toAuthentication());

		setNotCaching(response);
		return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenDto> refreshToken(@RequestParam("refreshToken") String refreshToken, HttpServletResponse response) {
		TokenDto tokenDto = authenticationService.reissueTokenDto(refreshToken);

		setNotCaching(response);
		return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
	}

	private HttpServletResponse setNotCaching(HttpServletResponse response) {
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		response.setHeader(HttpHeaders.PRAGMA, "no-store");
		response.setHeader(HttpHeaders.EXPIRES, "0");

		return response;
	}

}
