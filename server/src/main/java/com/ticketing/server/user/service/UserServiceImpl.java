package com.ticketing.server.user.service;

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

	@Override
	@Transactional
	public Optional<User> register(@Valid SignUpDTO signUpDto) {
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if (user.isPresent()) {
			log.error("이미 존재하는 이메일이기 때문에 신규 회원가입을 진행할 수 없습니다. :: {}", signUpDto);
			return Optional.empty();
		}

		User newUser = userRepository.save(signUpDto.toUser());
		return Optional.of(newUser);
	}

	@Override
	@Transactional
	public Optional<User> delete(@Valid DeleteUserDTO deleteUser) {
		Optional<User> optionalUser = userRepository.findByEmail(deleteUser.getEmail());
		if (optionalUser.isEmpty()) {
			log.error("존재하지 않는 이메일 입니다. :: {}", deleteUser);
			return Optional.empty();
		}

		User user = optionalUser.get();
		return Optional.of(user.delete(deleteUser));
	}

	@Override
	@Transactional
	public Optional<User> modifyPassword(@Valid ChangePasswordDTO changePassword) {
		Optional<User> optionalUser = userRepository.findByEmailAndIsDeletedFalse(changePassword.getEmail());
		if (optionalUser.isEmpty()) {
			log.error("존재하지 않는 이메일 입니다. :: {}", changePassword);
			return Optional.empty();
		}

		User user = optionalUser.get();
		return Optional.of(user.modifyPassword(changePassword));
	}

}
