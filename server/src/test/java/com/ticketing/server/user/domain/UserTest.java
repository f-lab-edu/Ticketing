package com.ticketing.server.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ticketing.server.global.exception.AlreadyDeletedException;
import com.ticketing.server.global.exception.PasswordMismatchException;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserTest;
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

class UserTest {

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
			.isInstanceOf(PasswordMismatchException.class);
	}

	@ParameterizedTest
	@MethodSource("provideDeleteUsers")
	@DisplayName("이미 회원탈퇴 되어 있는 경우")
	void alreadyDeletedException(DeleteUserDTO deleteUser) {
		// given
		User user = users.get(deleteUser.getEmail());

		// when
		user.delete(deleteUser);

		// then
		assertThatThrownBy(() -> user.delete(deleteUser))
			.isInstanceOf(AlreadyDeletedException.class);
	}

	@ParameterizedTest
	@MethodSource("provideDeleteUsers")
	@DisplayName("회원탈퇴 성공")
	void deleteSuccess(DeleteUserDTO deleteUser) {
		// given
		User user = users.get(deleteUser.getEmail());

		// when
		User deletedUser = user.delete(deleteUser);

		// then
		assertAll(
			() -> assertThat(deletedUser.getDeletedAt()).isNotNull()
			, () -> assertThat(deletedUser.isDeleted()).isTrue()
		);
	}

	@Test
	@DisplayName("입력받은 패스워드와 불일치로 변경 실패")
	void changePasswordFail() {
		// given
		ChangePasswordDTO changePassword = new ChangePasswordDTO("ticketing@gmail.com", "1234567", "ticketing1234", DeleteUserTest.CUSTOM_PASSWORD_ENCODER);
		User user = new User("유저1", "ticketing@gmail.com", "123456", UserGrade.GUEST, "010-1234-5678");

		// when
		// then
		assertThatThrownBy(() -> user.changePassword(changePassword))
			.isInstanceOf(PasswordMismatchException.class);
	}

	@Test
	@DisplayName("패스워드 변경 성공")
	void changePasswordSuccess() {
		// given
		ChangePasswordDTO changePassword = new ChangePasswordDTO("ticketing@gmail.com", "123456", "ticketing1234", DeleteUserTest.CUSTOM_PASSWORD_ENCODER);
		User user = new User("유저1", "ticketing@gmail.com", "123456", UserGrade.GUEST, "010-1234-5678");
		String oldPassword = user.getPassword();

		// when
		User modifiedUser = user.changePassword(changePassword);

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
		User user = new User("유저1", "email@gmail.com", "testPassword01", null, "010-1234-5678");

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
			new User("유저1", "ticketing1@gmail.com", "123456", UserGrade.GUEST, "010-1234-5678")
			, new User("유저2", "ticketing2@gmail.com", "qwe123", UserGrade.GUEST, "010-2234-5678")
			, new User("유저3", "ticketing3@gmail.com", "ticketing", UserGrade.STAFF, "010-3234-5678")
			, new User("유저4", "ticketing4@gmail.com", "ticketing123456", UserGrade.STAFF, "010-4234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfName() {
		return Stream.of(
			new User(null, "ticketing1@gmail.com", "123456", UserGrade.GUEST, "010-1234-5678")
			, new User("", "ticketing2@gmail.com", "qwe123", UserGrade.GUEST, "010-2234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfEmail() {
		return Stream.of(
			new User("유저1", null, "123456", UserGrade.GUEST, "010-1234-5678")
			, new User("유저2", "", "qwe123", UserGrade.GUEST, "010-2234-5678")
		);
	}

	public static Stream<User> provideValidationFailedOfEmail() {
		return Stream.of(
			new User("유저1", "email", "123456", UserGrade.GUEST, "010-1234-5678")
			, new User("유저2", "@gmail.com", "qwe123", UserGrade.GUEST, "010-2234-5678")
			, new User("유저3", "12Bye#domain.com", "ticketing", UserGrade.STAFF, "010-3234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfPassword() {
		return Stream.of(
			new User("유저1", "ticketing1@gmail.com", null, UserGrade.GUEST, "010-1234-5678")
			, new User("유저2", "ticketing2@gmail.com", "", UserGrade.GUEST, "010-2234-5678")
		);
	}

	public static Stream<User> provideNullOrEmptyOfPhone() {
		return Stream.of(
			new User("유저1", "ticketing1@gmail.com", "123456", UserGrade.GUEST, null)
			, new User("유저2", "ticketing2@gmail.com", "qwe123", UserGrade.GUEST, "")
		);
	}

	public static Stream<User> provideValidationFailedOfPhone() {
		return Stream.of(
			new User("유저1", "ticketing1@gmail.com", "123456", UserGrade.GUEST, "010-123-1234")
			, new User("유저2", "ticketing2@gmail.com", "qwe123", UserGrade.GUEST, "02-0444-4044")
			, new User("유저3", "ticketing3@gmail.com", "ticketing", UserGrade.STAFF, "033-7953")
			, new User("유저4", "ticketing4@gmail.com", "ticketing123456", UserGrade.STAFF, "033-0455-504")
		);
	}

	public static Stream<DeleteUserDTO> provideDifferentPasswordDeleteUsers() {
		return Stream.of(
			new DeleteUserDTO("ticketing1@gmail.com", "1234561", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing2@gmail.com", "qwe1231", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing3@gmail.com", "ticketing1", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing4@gmail.com", "ticketing1234561", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
		);
	}

	public static Stream<DeleteUserDTO> provideDeleteUsers() {
		return Stream.of(
			new DeleteUserDTO("ticketing1@gmail.com", "123456", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing2@gmail.com", "qwe123", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing3@gmail.com", "ticketing", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
			, new DeleteUserDTO("ticketing4@gmail.com", "ticketing123456", DeleteUserTest.CUSTOM_PASSWORD_ENCODER)
		);
	}

}
