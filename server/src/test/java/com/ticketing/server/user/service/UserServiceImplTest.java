package com.ticketing.server.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.user.domain.SequenceGenerator;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.repository.UserRepository;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.ChangedPasswordUserDTO;
import com.ticketing.server.user.service.dto.CreatedUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTOTest;
import com.ticketing.server.user.service.dto.DeletedUserDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	User user;
	SignUpDTO signUpDto;
	DeleteUserDTO deleteUserDto;
	ChangePasswordDTO changePasswordDto;

	@Mock
	UserRepository userRepository;

	@Mock
	SequenceGenerator sequenceGenerator;

	@InjectMocks
	UserServiceImpl userService;

	@BeforeEach
	void init() {
		signUpDto = new SignUpDTO("유저", "ticketing@gmail.com", "123456", "010-1234-5678");
		user = new User(111L, "유저", "ticketing@gmail.com", "123456", UserGrade.USER, "010-1234-5678");
		deleteUserDto = new DeleteUserDTO("ticketing@gmail.com", "123456", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER);
		changePasswordDto = new ChangePasswordDTO("ticketing@gmail.com", "123456", "ticketing1234", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER);
	}

	@Test
	@DisplayName("이미 동일한 이메일이 있을 경우")
	void duplicateEmailException() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		// then
		assertThatThrownBy(() -> userService.register(signUpDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("회원가입 성공했을 경우")
	void registerSuccess() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.empty());
		when(userRepository.save(any())).thenReturn(user);
		when(sequenceGenerator.generateId()).thenReturn(123L);

		// when
		CreatedUserDTO createdUserDto = userService.register(signUpDto);

		// then
		assertThat(createdUserDto).isNotNull();
	}

	@Test
	@DisplayName("회원탈퇴 시 이메일이 존재하지 않을 경우")
	void deleteFail() {
		// given
		when(userRepository.findByEmailAndDeletedAtNull("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.delete(deleteUserDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("회원탈퇴 성공했을 경우")
	void deleteSuccess() {
		// given
		when(userRepository.findByEmailAndDeletedAtNull("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		DeletedUserDTO deletedUserDto = userService.delete(deleteUserDto);

		// then
		assertThat(deletedUserDto.getDeletedAt()).isNotNull();
	}

	@Test
	@DisplayName("패스워드 변경 시 이메일이 존재하지 않을 경우")
	void changePasswordFail() {
		// given
		when(userRepository.findByEmailAndDeletedAtNull("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.changePassword(changePasswordDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("패스워드 변경 성공했을 경우")
	void changePasswordSuccess() {
		// given
		when(userRepository.findByEmailAndDeletedAtNull("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		ChangedPasswordUserDTO changedUserDto = userService.changePassword(changePasswordDto);

		// then
		assertThat(changedUserDto).isNotNull();
	}

	@Test
	@DisplayName("email 로 유저 디테일 정보 가져오기 성공했을 경우")
	void findDetailByEmailSuccess() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.of(user));

		// when
		UserDetailDTO userDetail = userService.findDetailByEmail("ticketing@gmail.com");

		// then
		assertAll(
			() -> assertThat(userDetail.getEmail()).isEqualTo("ticketing@gmail.com"),
			() -> assertThat(userDetail.getName()).isEqualTo("유저"),
			() -> assertThat(userDetail.getPhone()).isEqualTo("010-1234-5678"),
			() -> assertThat(userDetail.getGrade()).isEqualTo(UserGrade.USER)
		);
	}

	@Test
	@DisplayName("email 로 유저 디테일 정보 가져오기 실패했을 경우")
	void findDetailByEmailFail() {
		// given
		when(userRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> userService.findDetailByEmail("ticketing@gmail.com"))
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.EMAIL_NOT_FOUND);
	}

}
