package com.ticketing.server.user.application;

import static com.ticketing.server.user.domain.UserGrade.ROLES.ADMIN;
import static com.ticketing.server.user.domain.UserGrade.ROLES.USER;

import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.request.UserChangeGradeRequest;
import com.ticketing.server.user.application.request.UserChangePasswordRequest;
import com.ticketing.server.user.application.request.UserDeleteRequest;
import com.ticketing.server.user.application.response.PaymentsResponse;
import com.ticketing.server.user.application.response.SignUpResponse;
import com.ticketing.server.user.application.response.UserChangePasswordResponse;
import com.ticketing.server.user.application.response.UserDeleteResponse;
import com.ticketing.server.user.application.response.UserDetailResponse;
import com.ticketing.server.user.domain.ChangeGradeDTO;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import com.ticketing.server.user.service.interfaces.UserApisService;
import com.ticketing.server.user.service.interfaces.UserService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	private final UserApisService userApisService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping
	public ResponseEntity<SignUpResponse> register(@RequestBody @Valid SignUpRequest request) {
		User user = userService.register(request.toSignUpDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.CREATED).body(SignUpResponse.from(user));
	}

	@GetMapping("/details")
	@Secured(USER)
	public ResponseEntity<UserDetailResponse> details(@AuthenticationPrincipal UserDetails userRequest) {
		UserDetailDTO userDetail = userService.findDetailByEmail(userRequest.getUsername());
		return ResponseEntity.status(HttpStatus.OK).body(userDetail.toResponse());
	}

	@DeleteMapping
	@Secured(USER)
	public ResponseEntity<UserDeleteResponse> deleteUser(@RequestBody @Valid UserDeleteRequest request) {
		User user = userService.delete(request.toDeleteUserDto(passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserDeleteResponse.from(user));
	}

	@PutMapping("/password")
	@Secured(USER)
	public ResponseEntity<UserChangePasswordResponse> changePassword(
		@AuthenticationPrincipal UserDetails userRequest,
		@RequestBody @Valid UserChangePasswordRequest request) {
		User user = userService.changePassword(request.toChangePasswordDto(userRequest.getUsername(), passwordEncoder));
		return ResponseEntity.status(HttpStatus.OK).body(UserChangePasswordResponse.from(user));
	}

	@PostMapping("/grade")
	@Secured(ADMIN)
	public ResponseEntity<UserChangeGradeResponse> changeGrade(@RequestBody @Valid UserChangeGradeRequest request) {
		ChangeGradeDTO changeGradeDto = userService.changeGrade(request.getEmail(), request.getAfterGrade());
		return ResponseEntity.status(HttpStatus.OK).body(changeGradeDto.toResponse());
	}

	@GetMapping("/payments")
	@Secured(USER)
	public ResponseEntity<PaymentsResponse> getPayments(@AuthenticationPrincipal UserDetails userRequest) {
		PaymentsResponse paymentDetails = userApisService.findPaymentsByEmail(userRequest.getUsername());
		return ResponseEntity.status(HttpStatus.OK).body(paymentDetails);
	}

}
