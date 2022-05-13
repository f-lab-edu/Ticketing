package com.ticketing.server.user.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.global.validator.constraints.Phone;
import java.time.LocalDateTime;
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

	@Column(name = "name")
	@NotEmpty(message = "이름은 필수 입니다.")
	private String name;

	@Column(name = "email")
	@NotEmpty(message = "이메일은 필수 입니다.")
	@Email(message = "이메일이 올바르지 않습니다.")
	private String email;

	@Column(name = "password")
	@NotEmpty(message = "패스워드는 필수 입니다.")
	private String password;

	@Column(name = "grade")
	@NotNull(message = "사용자 등급은 필수 입니다.")
	@Enumerated(value = EnumType.STRING)
	private UserGrade grade;

	@Column(name = "phone")
	@NotEmpty(message = "휴대번호는 필수 입니다.")
	@Phone(message = "휴대번호가 올바르지 않습니다.")
	private String phone;

	private boolean isDeleted = false;

	private LocalDateTime deletedAt;

	public User(String name, String email, String password, UserGrade grade, String phone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.grade = grade;
		this.phone = phone;
	}

}
