package com.ticketing.server.user.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.validator.constraints.Phone;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.PasswordMatches;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity {

	@Column(name = "alternate_key", unique = true, nullable = false)
	@NotNull(message = "{validation.not.null.alternate.key}")
	private Long alternateId;

	@Column(name = "name", nullable = false)
	@NotEmpty(message = "{validation.not.empty.name}")
	private String name;

	@Column(name = "email", unique = true, nullable = false)
	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@Column(name = "password", nullable = false)
	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	@Column(name = "grade", nullable = false)
	@NotNull(message = "{validation.not.null.grade}")
	@Enumerated(value = EnumType.STRING)
	private UserGrade grade = UserGrade.USER;

	@Column(name = "phone", nullable = false)
	@NotEmpty(message = "{validation.not.empty.phone}")
	@Phone
	private String phone;

	public User(Long alternateId, String name, String email, String password, UserGrade grade, String phone) {
		this.alternateId = alternateId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.grade = grade;
		this.phone = phone;
	}

	User(Long id, Long alternateId, String name, String email, String password, UserGrade grade, String phone) {
		this.id = id;
		this.alternateId = alternateId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.grade = grade;
		this.phone = phone;
	}

	public User delete(DeleteUserDTO deleteUser) {
		if (deletedAt != null) {
			throw ErrorCode.throwDeletedEmail();
		}

		checkPassword(deleteUser);

		deletedAt = LocalDateTime.now();
		return this;
	}

	public User changePassword(ChangePasswordDTO changePassword) {
		checkPassword(changePassword);

		this.password = changePassword.getEncodePassword();
		return this;
	}

	public void checkPassword(PasswordMatches passwordMatches) {
		if (!passwordMatches.passwordMatches(password)) {
			throw ErrorCode.throwMismatchPassword();
		}
	}

}
