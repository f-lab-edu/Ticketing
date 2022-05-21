package com.ticketing.server.user.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.global.validator.constraints.Phone;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends AbstractEntity {

	public User(String name, String email, String password, UserGrade grade, String phone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.grade = grade;
		this.phone = phone;
	}

	@Column(name = "name")
	@NotEmpty(message = "{validation.not.empty.name}")
	private String name;

	@Column(name = "email")
	@NotEmpty(message = "{validation.not.empty.email}")
	@Email(message = "{validation.email}")
	private String email;

	@Column(name = "password")
	@NotEmpty(message = "{validation.not.empty.password}")
	private String password;

	@Column(name = "grade")
	@NotNull(message = "{validation.not.empty.grade}")
	@Enumerated(value = EnumType.STRING)
	private UserGrade grade = UserGrade.GUEST;

	@Column(name = "phone")
	@NotEmpty(message = "{validation.not.empty.phone}")
	@Phone
	private String phone;

	private boolean isDeleted = false;

	private LocalDateTime deletedAt;

	public Optional<User> delete() {
		if (isDeleted) {
			return Optional.empty();
		}

		isDeleted = true;
		deletedAt = LocalDateTime.now();
		return Optional.of(this);
	}

}
