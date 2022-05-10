package com.ticketing.server.user.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends AbstractEntity {

	@NotNull
	private String name;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private UserGrade grade;

	@NotNull
	private String phone;

	private boolean isDeleted = false;

	private LocalDateTime deletedAt;

	@Builder
	protected User(String name, String email, String password, UserGrade grade, String phone) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.grade = grade;
		this.phone = phone;
	}

}
