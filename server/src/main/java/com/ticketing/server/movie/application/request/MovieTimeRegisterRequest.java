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

	@NotNull(message = "{validation.not.null.theaterId}")
	private Long theaterId;

	@NotNull(message = "{validation.not.null.round}")
	private Integer round;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime startAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime endAt;

	public MovieTimeRegisterDTO toMovieTimeRegisterDTO() {
		return new MovieTimeRegisterDTO(movieId, theaterId, round, startAt, endAt);
	}

}
