package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.movie.service.dto.MovieTimeDto;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
	private Integer round;

	@NotNull
	private LocalDateTime startAt;

	@NotNull
	private LocalDateTime endAt;

}
