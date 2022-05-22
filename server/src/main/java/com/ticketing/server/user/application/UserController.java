package com.ticketing.server.user.application;

import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.request.UserDeleteRequest;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.UserServiceImpl;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserServiceImpl userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping
	public ResponseEntity<Object> register(@RequestBody @Valid SignUpRequest signUpRequest) {
		Optional<User> user = userService.register(signUpRequest.toSignUp(passwordEncoder));

		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestBody @Valid UserDeleteRequest userDeleteRequest) {
		Optional<User> user = userService.delete(userDeleteRequest.toDeleteUser(passwordEncoder));

		if (user.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
