package com.ticketing.server.user.service;

import com.ticketing.server.user.domain.User;
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

	@Override
	@Transactional
	public User register(@Valid SignUp signUpDto) {
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

		if (user.isPresent()) {
			throw new DuplicateEmailException(signUpDto.getEmail());
		}

		return userRepository.save(signUpDto.toUser(passwordEncoder));
	}

}
