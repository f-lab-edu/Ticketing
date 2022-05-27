package com.ticketing.server.user.service;

import com.ticketing.server.global.exception.NotFoundEmailException;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.LoginDTO;
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
	public User login(LoginDTO loginDto) {
		User user = findNotDeletedUserByEmail(loginDto.getEmail());
		user.checkPassword(loginDto);
		return user;
	}

	@Override
	@Transactional
	public User register(@Valid SignUpDTO signUpDto) {
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if (user.isPresent()) {
			log.error("이미 존재하는 이메일이기 때문에 신규 회원가입을 진행할 수 없습니다. :: {}", signUpDto);
			throw new IllegalArgumentException("이미 존재하는 이메일이기 때문에 신규 회원가입을 진행할 수 없습니다.");
		}

		return userRepository.save(signUpDto.toUser());
	}

	@Override
	@Transactional
	public User delete(@Valid DeleteUserDTO deleteUserDto) {
		Optional<User> optionalUser = userRepository.findByEmail(deleteUserDto.getEmail());
		if (optionalUser.isEmpty()) {
			log.error("존재하지 않는 이메일 입니다. :: {}", deleteUserDto.getEmail());
			throw new NotFoundEmailException();
		}

		User user = optionalUser.get();
		return user.delete(deleteUserDto);
	}

	@Override
	@Transactional
	public User changePassword(@Valid ChangePasswordDTO changePasswordDto) {
		User user = findNotDeletedUserByEmail(changePasswordDto.getEmail());
		return user.changePassword(changePasswordDto);
	}

	private User findNotDeletedUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmailAndIsDeletedFalse(email);
		if (optionalUser.isEmpty()) {
			log.error("존재하지 않는 이메일 입니다. :: {}", email);
			throw new NotFoundEmailException();
		}
		return optionalUser.get();
	}

}
