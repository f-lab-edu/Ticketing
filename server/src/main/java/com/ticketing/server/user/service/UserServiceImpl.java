package com.ticketing.server.user.service;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.exception.DuplicateEmailException;
import com.ticketing.server.user.service.dto.SignUp;
import com.ticketing.server.user.service.interfaces.UserService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class UserServiceImpl implements UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Transactional
	public User register(@Valid SignUp signUpDto) {
		validateEmail(signUpDto.getEmail());

		User user = new User(signUpDto.getName(),
			signUpDto.getEmail(),
			signUpDto.encodePassword(passwordEncoder),
			UserGrade.GUEST,
			signUpDto.getPhone());

		return userRepository.save(user);
	}

	private void validateEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);

		if (user.isPresent()) {
			throw new DuplicateEmailException(email);
		}
	}

}
