package com.ticketing.server.user.application;

import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.request.UserDeleteRequest;
import com.ticketing.server.user.application.request.UserModifyPasswordRequest;
import com.ticketing.server.user.application.response.SignUpResponse;
import com.ticketing.server.user.application.response.UserDeleteResponse;
import com.ticketing.server.user.application.response.UserChangePasswordResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.UserServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

	private final UserServiceImpl userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping
	public ResponseEntity<Object> register(@RequestBody @Valid SignUpRequest request) {
		User user = userService.register(request.toSignUp(passwordEncoder));
		return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResponse.of(user));
	}

	@DeleteMapping
	public ResponseEntity<Object> deleteUser(@RequestBody @Valid UserDeleteRequest request) {
		User user = userService.delete(request.toDeleteUser(passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserDeleteResponse.of(user));
	}

	@PatchMapping("/password")
	public ResponseEntity<Object> changePassword(@RequestBody @Valid UserModifyPasswordRequest request) {
		if (request.oldEqualNew()) {
			log.error("기존 패스워드와 동일한 패스워드로 변경할 수 없습니다.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		User user = userService.changePassword(request.toChangePassword(passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserChangePasswordResponse.of(user));
	}

}
