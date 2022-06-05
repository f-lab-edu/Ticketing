package com.ticketing.server.user.application;

import com.ticketing.server.global.jwt.JwtProperties;
import com.ticketing.server.user.application.request.LoginRequest;
import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.request.UserDeleteRequest;
import com.ticketing.server.user.application.request.UserModifyPasswordRequest;
import com.ticketing.server.user.application.response.LoginResponse;
import com.ticketing.server.user.application.response.SignUpResponse;
import com.ticketing.server.user.application.response.UserChangePasswordResponse;
import com.ticketing.server.user.application.response.UserDeleteResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.UserServiceImpl;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j

public class UserController {

	private final UserServiceImpl userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtProperties jwtProperties;

	@PostMapping
	public ResponseEntity<SignUpResponse> register(@RequestBody @Valid SignUpRequest request) {
		User user = userService.register(request.toSignUpDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResponse.of(user));
	}

	@DeleteMapping
	@Secured("ROLE_GUEST")
	public ResponseEntity<UserDeleteResponse> deleteUser(@RequestBody @Valid UserDeleteRequest request) {
		User user = userService.delete(request.toDeleteUserDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserDeleteResponse.of(user));
	}

	@PatchMapping("/password")
	@Secured("ROLE_GUEST")
	public ResponseEntity<UserChangePasswordResponse> changePassword(@RequestBody @Valid UserModifyPasswordRequest request) {
		if (request.oldEqualNew()) {
			log.error("기존 패스워드와 동일한 패스워드로 변경할 수 없습니다.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		User user = userService.changePassword(request.toChangePasswordDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserChangePasswordResponse.of(user));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		String accessToken = userService.login(loginRequest.toAuthentication());

		response.setHeader(jwtProperties.getAccessHeader(), accessToken);
		return ResponseEntity.status(HttpStatus.OK).body(LoginResponse.of(accessToken));
	}

}
