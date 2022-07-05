package com.ticketing.server.user.service;

import static com.ticketing.server.global.exception.ErrorCode.DUPLICATE_EMAIL;
import static com.ticketing.server.global.exception.ErrorCode.EMAIL_NOT_FOUND;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.user.api.PaymentClient;
import com.ticketing.server.user.api.dto.request.SimplePaymentsRequest;
import com.ticketing.server.user.api.dto.response.SimplePaymentsResponse;
import com.ticketing.server.user.application.response.SimplePaymentDetailsResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
import com.ticketing.server.user.service.interfaces.UserService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PaymentClient paymentClient;

	@Override
	@Transactional
	public User register(@Valid SignUpDTO signUpDto) {
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if (user.isEmpty()) {
			return userRepository.save(signUpDto.toUser());
		}

		throw new TicketingException(DUPLICATE_EMAIL);
	}

	@Override
	@Transactional
	public User delete(@Valid DeleteUserDTO deleteUserDto) {
		User user = findNotDeletedUserByEmail(deleteUserDto.getEmail());
		return user.delete(deleteUserDto);
	}

	@Override
	@Transactional
	public User changePassword(@Valid ChangePasswordDTO changePasswordDto) {
		User user = findNotDeletedUserByEmail(changePasswordDto.getEmail());
		return user.changePassword(changePasswordDto);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(UserServiceImpl::throwEmailNotFound);
	}

	@Override
	public SimplePaymentDetailsResponse findSimplePaymentDetails(String email) {
		User user = findNotDeletedUserByEmail(email);
		SimplePaymentsResponse simplePayments = paymentClient.getSimplePayments(SimplePaymentsRequest.from(user));

		return SimplePaymentDetailsResponse.of(email, simplePayments);
	}

	private User findNotDeletedUserByEmail(String email) {
		return userRepository.findByEmailAndIsDeletedFalse(email)
			.orElseThrow(UserServiceImpl::throwEmailNotFound);
	}

	private static RuntimeException throwEmailNotFound() {
		throw new TicketingException(EMAIL_NOT_FOUND);
	}

}
