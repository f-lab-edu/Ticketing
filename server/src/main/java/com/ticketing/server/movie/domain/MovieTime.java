package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.movie.service.dto.MovieTimeDto;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class MovieTime extends AbstractEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "movie_id", referencedColumnName = "id", updatable = false)
	private Movie movie;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "theater_id", referencedColumnName = "id", updatable = false)
	private Theater theater;

	@NotNull
	private LocalDate runningDate;

	@NotNull
	private Integer round;

	@NotNull
	private LocalTime startAt;

	@NotNull
	private LocalTime endAt;

	public MovieTimeDto toDto() {
		return new MovieTimeDto(this.getId(), this.round, this.theater.getTheaterNumber(), this.startAt, this.endAt);
	}

}
