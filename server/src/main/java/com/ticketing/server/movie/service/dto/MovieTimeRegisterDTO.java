package com.ticketing.server.movie.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieTimeRegisterDTO {

	@NotNull(message = "{validation.not.null.movieId}")
	private Long movieId;

	@NotNull(message = "{validation.not.null.theaterNumber}")
	private Integer theaterNumber;

	@NotNull(message = "{validation.not.null.round}")
	private Integer round;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime startAt;

}
