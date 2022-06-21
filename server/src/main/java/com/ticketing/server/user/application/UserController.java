package com.ticketing.server.user.application;

import com.ticketing.server.global.security.jwt.JwtProperties;
import com.ticketing.server.user.application.request.LoginRequest;
import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.request.UserDeleteRequest;
import com.ticketing.server.user.application.request.UserModifyPasswordRequest;
import com.ticketing.server.user.application.response.SignUpResponse;
import com.ticketing.server.user.application.response.TokenDto;
import com.ticketing.server.user.application.response.UserChangePasswordResponse;
import com.ticketing.server.user.application.response.UserDeleteResponse;
import com.ticketing.server.user.application.response.UserDetailResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.UserServiceImpl;
import com.ticketing.server.user.service.interfaces.AuthenticationService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

	private final UserServiceImpl userService;
	private final AuthenticationService authenticationService;
	private final PasswordEncoder passwordEncoder;
	private final JwtProperties jwtProperties;

	@PostMapping
	public ResponseEntity<SignUpResponse> register(@RequestBody @Valid SignUpRequest request) {
		User user = userService.register(request.toSignUpDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResponse.from(user));
	}

	@GetMapping("/info")
	@Secured("ROLE_GUEST")
	public ResponseEntity<UserDetailResponse> myInfo(@AuthenticationPrincipal UserDetails userRequest) {
		User user = userService.findByEmail(userRequest.getUsername());
		return ResponseEntity.status(HttpStatus.OK).body(UserDetailResponse.from(user));
	}

	@DeleteMapping
	@Secured("ROLE_GUEST")
	public ResponseEntity<UserDeleteResponse> deleteUser(@RequestBody @Valid UserDeleteRequest request) {
		User user = userService.delete(request.toDeleteUserDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserDeleteResponse.from(user));
	}

	@PutMapping("/password")
	@Secured("ROLE_GUEST")
	public ResponseEntity<UserChangePasswordResponse> changePassword(@RequestBody @Valid UserModifyPasswordRequest request) {
		if (request.oldEqualNew()) {
			log.error("기존 패스워드와 동일한 패스워드로 변경할 수 없습니다.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		User user = userService.changePassword(request.toChangePasswordDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserChangePasswordResponse.from(user));
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
		TokenDto tokenDto = authenticationService.login(loginRequest.toAuthentication());

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-store");
		return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenDto> refreshToken(@RequestParam("refreshToken") String refreshToken, HttpServletResponse response) {
		TokenDto tokenDto = authenticationService.reissueAccessToken(refreshToken);

		response.setHeader(jwtProperties.getAccessHeader(), tokenDto.getAccessToken());
		response.setHeader(jwtProperties.getRefreshHeader(), tokenDto.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body(tokenDto);
	}

}
