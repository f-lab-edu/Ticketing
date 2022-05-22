package com.ticketing.server.user.application;

import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.request.UserDeleteRequest;
import com.ticketing.server.user.application.request.UserPasswordModifyRequest;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.UserServiceImpl;
import java.util.Optional;
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
	public ResponseEntity<Object> register(@RequestBody @Valid SignUpRequest signUpRequest) {
		Optional<User> user = userService.register(signUpRequest.toSignUp(passwordEncoder));

		if (user.isEmpty()) {
			log.error("이미 존재하는 이메일 입니다. :: {}", signUpRequest.getEmail());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestBody @Valid UserDeleteRequest userDeleteRequest) {
		Optional<User> user = userService.delete(userDeleteRequest.toDeleteUser(passwordEncoder));

		if (user.isEmpty()) {
			log.error("존재하지 않는 이메일 입니다. :: {}", userDeleteRequest.getEmail());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PatchMapping("/password")
	public ResponseEntity<Object> modifyPassword(@RequestBody @Valid UserPasswordModifyRequest request) {
		if (request.oldEqualNew()) {
			log.error("기존 패스워드와 동일한 패스워드로 변경할 수 없습니다.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Optional<User> user = userService.modifyPassword(request.toChangePassword(passwordEncoder));

		if (user.isEmpty()) {
			log.error("존재하지 않는 이메일 입니다. :: {}", request.getEmail());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
