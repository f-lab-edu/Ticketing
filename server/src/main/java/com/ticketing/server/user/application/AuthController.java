package com.ticketing.server.user.application;

import com.ticketing.server.user.application.request.LoginRequest;
import com.ticketing.server.user.application.response.LogoutResponse;
import com.ticketing.server.user.application.response.TokenDto;
import com.ticketing.server.user.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
	public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest) {
		TokenDto tokenDto = authenticationService.generateTokenDto(loginRequest.toAuthentication());

		return ResponseEntity.status(HttpStatus.OK)
			.headers(getHttpHeaders())
			.body(tokenDto);
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenDto> refreshToken(@RequestParam("refreshToken") String refreshToken) {
		TokenDto tokenDto = authenticationService.reissueTokenDto(refreshToken);

		return ResponseEntity.status(HttpStatus.OK)
			.headers(getHttpHeaders())
			.body(tokenDto);
	}

	@PostMapping("/logout")
	public ResponseEntity<LogoutResponse> logout(@AuthenticationPrincipal UserDetails userRequest) {
		LogoutResponse logoutResponse = authenticationService.deleteRefreshToken(userRequest.getUsername());

		return ResponseEntity.status(HttpStatus.OK)
			.body(logoutResponse);
	}

	private HttpHeaders getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
		httpHeaders.set(HttpHeaders.PRAGMA, "no-store");
		httpHeaders.set(HttpHeaders.EXPIRES, "0");

		return httpHeaders;
	}

}
