package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Movie extends AbstractEntity {

	@NotNull
	@Column(unique = true)
	private String title;

	@NotNull
	private Long runningTime;

	Movie(Long id, String title, Long runningTime) {
		this.id = id;
		this.title = title;
		this.runningTime = runningTime;
	}

}
