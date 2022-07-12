package com.ticketing.server.user.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.user.domain.SequenceGenerator;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.ChangedPasswordUserDTO;
import com.ticketing.server.user.service.dto.CreatedUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.DeletedUserDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import com.ticketing.server.user.service.interfaces.UserService;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
	private final SequenceGenerator sequenceGenerator;

	@Override
	@Transactional
	public CreatedUserDTO register(@Valid SignUpDTO signUpDto) {
		Optional<User> optionalUser = userRepository.findByEmail(signUpDto.getEmail());
		if (optionalUser.isEmpty()) {
			User user = userRepository.save(signUpDto.toUser(sequenceGenerator.generateId()));
			return new CreatedUserDTO(user);
		}

		throw ErrorCode.throwDuplicateEmail();
	}

	@Override
	@Transactional
	public DeletedUserDTO delete(@Valid DeleteUserDTO deleteUserDto) {
		User user = findNotDeletedUserByEmail(deleteUserDto.getEmail())
			.delete(deleteUserDto);

		return new DeletedUserDTO(user);
	}

	@Override
	@Transactional
	public ChangedPasswordUserDTO changePassword(@Valid ChangePasswordDTO changePasswordDto) {
		User user = findNotDeletedUserByEmail(changePasswordDto.getEmail())
			.changePassword(changePasswordDto);

		return new ChangedPasswordUserDTO(user);
	}

	@Override
	public UserDetailDTO findDetailByEmail(@NotNull String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(ErrorCode::throwEmailNotFound);

		return new UserDetailDTO(user);
	}

	@Override
	public User findNotDeletedUserByEmail(@NotNull String email) {
		return userRepository.findByEmailAndDeletedAtNull(email)
			.orElseThrow(ErrorCode::throwEmailNotFound);
	}

}
