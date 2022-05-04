package com.ticketing.server.user.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
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

}
