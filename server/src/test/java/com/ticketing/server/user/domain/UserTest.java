package com.ticketing.server.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import java.util.Set;
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

	private static Validator validator;

	@BeforeEach
	void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

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

	@ParameterizedTest
	@MethodSource("provideCorrectUsers")
	@DisplayName("이미 회원탈퇴 되어 있는 경우")
	void deleteFail(User user) {
		// given
		user.delete();

		// when
		Optional<User> deletedUser = user.delete();

		// then
		assertThat(deletedUser).isEmpty();
	}

	@ParameterizedTest
	@MethodSource("provideCorrectUsers")
	@DisplayName("회원탈퇴 성공")
	void deleteSuccess(User user) {
		// given
		// when
		Optional<User> deletedUser = user.delete();

		// then
		assertThat(deletedUser).isPresent();
		assertAll(
			() -> assertThat(deletedUser.get().getDeletedAt()).isNotNull()
			, () -> assertThat(deletedUser.get().isDeleted()).isTrue()
		);
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

}
