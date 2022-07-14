package com.ticketing.server.movie.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ticketing.server.movie.service.dto.MovieTimeRegisterDTO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieTimeRegisterRequest {

	@NotNull(message = "{validation.not.null.movieId}")
	private Long movieId;

	@NotNull(message = "{validation.not.null.theaterNumber}")
	private Integer theaterNumber;

	@NotNull(message = "{validation.not.null.round}")
	private Integer round;

	private LocalDateTime startAt;

	public MovieTimeRegisterDTO toMovieTimeRegisterDTO() {
		return new MovieTimeRegisterDTO(movieId, theaterNumber, round, startAt);
	}

}
