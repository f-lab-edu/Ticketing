package com.ticketing.server.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

	private static Validator validator;

	@BeforeEach
	void init() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("유저 검증 성공")
	void validateSuccess() {
		// given
		User user = new User("유저1", "email@gmail.com", "testPassword01", UserGrade.GUEST, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).isEmpty();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("name null 혹은 빈값 검증")
	void nameNullOrEmpty(String name) {
		// given
		User user = new User(name, "email@gmail.com", "testPassword01", UserGrade.GUEST, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertAll(
			() -> assertThat(constraintViolations).hasSize(1),
			() -> assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("이름은 필수 입니다.")
		);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("email null or empty 검증")
	void emailNullOrEmpty(String email) {
		// given
		User user = new User("유저1", email, "testPassword01", UserGrade.GUEST, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertAll(
			() -> assertThat(constraintViolations).hasSize(1),
			() -> assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("이메일은 필수 입니다.")
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"email", "@hello.com", "12Bye#domain.com"})
	@DisplayName("email 실패 검증")
	void emailValid(String email) {
		// given
		User user = new User("유저1", email, "testPassword01", UserGrade.GUEST, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertAll(
			() -> assertThat(constraintViolations).hasSize(1),
			() -> assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("이메일이 올바르지 않습니다.")
		);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@DisplayName("password null 혹은 빈값 검증")
	void passwordNullOrEmpty(String password) {
		// given
		User user = new User("유저1", "email@gmail.com", password, UserGrade.GUEST, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertAll(
			() -> assertThat(constraintViolations).hasSize(1),
			() -> assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("패스워드는 필수 입니다.")
		);
	}

	@Test
	@DisplayName("grade null 검증")
	void gradeNull() {
		// given
		User user = new User("유저1", "email@gmail.com", "testPassword01", null, "010-1234-5678");

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertAll(
			() -> assertThat(constraintViolations).hasSize(1),
			() -> assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("사용자 등급은 필수 입니다.")
		);
	}

	@ParameterizedTest
	@DisplayName("phone null or empty 검증")
	@NullAndEmptySource
	void phoneNullOrEmpty(String phone) {
		// given
		User user = new User("유저1", "email@gmail.com", "testPassword01", UserGrade.GUEST, phone);

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertAll(
			() -> assertThat(constraintViolations).hasSize(1),
			() -> assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("휴대번호는 필수 입니다.")
		);
	}

	@ParameterizedTest
	@DisplayName("phone 실패 검증")
	@ValueSource(strings = {"010-123-1234", "02-0444-4044", "033-7953", "033-0455-504"})
	void phoneValid(String phone) {
		// given
		User user = new User("유저1", "email@gmail.com", "testPassword01", UserGrade.GUEST, phone);

		// when
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

		// then
		assertThat(constraintViolations).hasSize(1);
		assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("휴대번호가 올바르지 않습니다.");
	}

}
