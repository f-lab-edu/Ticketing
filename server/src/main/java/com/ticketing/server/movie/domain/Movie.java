package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.service.dto.MovieDTO;
import java.time.LocalDateTime;
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
	private String title;

	@NotNull
	private Long runningTime;

	Movie(Long id, String title, Long runningTime) {
		this.id = id;
		this.title = title;
		this.runningTime = runningTime;
	}

	public Movie delete() {
		if (deletedAt != null) {
			throw ErrorCode.throwDeletedMovie();
		}

		deletedAt = LocalDateTime.now();

		return this;
	}

	public MovieDTO toMovieDTO() {
		return new MovieDTO(this.id, this.title);
	}

}
