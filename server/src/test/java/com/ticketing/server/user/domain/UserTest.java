package com.ticketing.server.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTOTest;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class UserTest {

	private Validator validator;
	private Map<String, User> users;

	@BeforeEach
	void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		users = provideCorrectUsers().collect(Collectors.toMap(User::getEmail, user -> user));
	}

	@ParameterizedTest
	@MethodSource("provideDifferentPasswordDeleteUsers")
	@DisplayName("입력된 패스워드가 다를 경우")
	void passwordMismatchException(DeleteUserDTO deleteUser) {
		// given
		User user = users.get(deleteUser.getEmail());

		// when
		// then
		assertThatThrownBy(() -> user.delete(deleteUser))
			.isInstanceOf(TicketingException.class);
	}

	@ParameterizedTest
	@MethodSource("provideDeleteUsers")
	@DisplayName("이미 회원탈퇴 되어 있는 경우")
	void alreadyDeletedException(DeleteUserDTO deleteUserDto) {
		// given
		User user = users.get(deleteUserDto.getEmail());

		// when
		user.delete(deleteUserDto);

		// then
		assertThatThrownBy(() -> user.delete(deleteUserDto))
			.isInstanceOf(TicketingException.class);

	}

	@ParameterizedTest
	@MethodSource("provideDeleteUsers")
	@DisplayName("회원탈퇴 성공")
	void deleteSuccess(DeleteUserDTO deleteUserDto) {
		// given
		User user = users.get(deleteUserDto.getEmail());

		// when
		User deletedUser = user.delete(deleteUserDto);

		// then
		assertThat(deletedUser.getDeletedAt()).isNotNull();
	}

	@Test
	@DisplayName("입력받은 패스워드와 불일치로 변경 실패")
	void changePasswordFail() {
		// given
		ChangePasswordDTO changePasswordDto = new ChangePasswordDTO("ticketing1@gmail.com", "1234567", "ticketing1234", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER);
		User user = users.get(changePasswordDto.getEmail());

		// when
		// then
		assertThatThrownBy(() -> user.changePassword(changePasswordDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("패스워드 변경 성공")
	void changePasswordSuccess() {
		// given
		ChangePasswordDTO changePasswordDto = new ChangePasswordDTO("ticketing1@gmail.com", "123456", "ticketing1234", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER);
		User user = users.get(changePasswordDto.getEmail());
		String oldPassword = user.getPassword();

		// when
		User modifiedUser = user.changePassword(changePasswordDto);

		// then
		assertThat(modifiedUser.getPassword()).isNotEqualTo(oldPassword);
	}

	@ParameterizedTest
	@MethodSource("provideCorrectUsers")
	@DisplayName("유저 검증 성공")
	void validateSuccess(User user) {
		// given
		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).isEmpty();
	}

	@ParameterizedTest
	@MethodSource("provideNullOrEmptyOfName")
	@DisplayName("name null 혹은 빈값 검증")
	void nameNullOrEmpty(User user) {
		// given
		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	@ParameterizedTest
	@MethodSource("provideNullOrEmptyOfEmail")
	@DisplayName("email null or empty 검증")
	void emailNullOrEmpty(User user) {
		// given
		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	@ParameterizedTest
	@MethodSource("provideValidationFailedOfEmail")
	@DisplayName("email 실패 검증")
	void emailValid(User user) {
		// given
		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	@ParameterizedTest
	@MethodSource("provideNullOrEmptyOfPassword")
	@DisplayName("password null 혹은 빈값 검증")
	void passwordNullOrEmpty(User user) {
		// given
		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	@Test
	@DisplayName("grade null 검증")
	void gradeNull() {
		// given
		User user = new User(111L, "유저1", "email@gmail.com", "testPassword01", null, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	@Test
	@DisplayName("alternateId null 검증")
	void alternateIdNull() {
		// given
		User user = new User(null, "유저1", "email@gmail.com", "testPassword01", UserGrade.USER, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	@ParameterizedTest
	@MethodSource("provideNullOrEmptyOfPhone")
	@DisplayName("phone null or empty 검증")
	void phoneNullOrEmpty(User user) {
		// given
		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	@ParameterizedTest
	@MethodSource("provideValidationFailedOfPhone")
	@DisplayName("phone 실패 검증")
	void phoneValid(User user) {
		// given
		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
	}

	public static Stream<User> provideCorrectUsers() {
		return Stream.of(
			new User(1L, 111L, "유저1", "ticketing1@gmail.com", "123456", UserGrade.USER, "010-1234-5678")
			, new User(2L, 222L, "유저2", "ticketing2@gmail.com", "qwe123", UserGrade.USER, "010-2234-5678")
			, new User(3L, 333L, "유저3", "ticketing3@gmail.com", "ticketing", UserGrade.STAFF, "010-3234-5678")
			, new User(4L, 444L, "유저4", "ticketing4@gmail.com", "ticketing123456", UserGrade.STAFF, "010-4234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfName() {
		return Stream.of(
			new User(111L, null, "ticketing1@gmail.com", "123456", UserGrade.USER, "010-1234-5678")
			, new User(111L, "", "ticketing2@gmail.com", "qwe123", UserGrade.USER, "010-2234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfEmail() {
		return Stream.of(
			new User(111L, "유저1", null, "123456", UserGrade.USER, "010-1234-5678")
			, new User(111L, "유저2", "", "qwe123", UserGrade.USER, "010-2234-5678")
		);
	}

	public static Stream<User> provideValidationFailedOfEmail() {
		return Stream.of(
			new User(111L, "유저1", "email", "123456", UserGrade.USER, "010-1234-5678")
			, new User(111L, "유저2", "@gmail.com", "qwe123", UserGrade.USER, "010-2234-5678")
			, new User(111L, "유저3", "12Bye#domain.com", "ticketing", UserGrade.STAFF, "010-3234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfPassword() {
		return Stream.of(
			new User(111L, "유저1", "ticketing1@gmail.com", null, UserGrade.USER, "010-1234-5678")
			, new User(111L, "유저2", "ticketing2@gmail.com", "", UserGrade.USER, "010-2234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfPhone() {
		return Stream.of(
			new User(111L, "유저1", "ticketing1@gmail.com", "123456", UserGrade.USER, null)
			, new User(111L, "유저2", "ticketing2@gmail.com", "qwe123", UserGrade.USER, "")
		);
	}

	public static Stream<User> provideValidationFailedOfPhone() {
		return Stream.of(
			new User(111L, "유저1", "ticketing1@gmail.com", "123456", UserGrade.USER, "010-123-1234")
			, new User(111L, "유저2", "ticketing2@gmail.com", "qwe123", UserGrade.USER, "02-0444-4044")
			, new User(111L, "유저3", "ticketing3@gmail.com", "ticketing", UserGrade.STAFF, "033-7953")
			, new User(111L, "유저4", "ticketing4@gmail.com", "ticketing123456", UserGrade.STAFF, "033-0455-504")
		);
	}

	public static Stream<DeleteUserDTO> provideDifferentPasswordDeleteUsers() {
		return Stream.of(
			new DeleteUserDTO("ticketing1@gmail.com", "1234561", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing2@gmail.com", "qwe1231", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing3@gmail.com", "ticketing1", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing4@gmail.com", "ticketing1234561", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
		);
	}

	public static Stream<DeleteUserDTO> provideDeleteUsers() {
		return Stream.of(
			new DeleteUserDTO("ticketing1@gmail.com", "123456", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing2@gmail.com", "qwe123", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing3@gmail.com", "ticketing", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing4@gmail.com", "ticketing123456", DeleteUserDTOTest.CUSTOM_PASSWORD_ENCODER)
		);
	}

}
