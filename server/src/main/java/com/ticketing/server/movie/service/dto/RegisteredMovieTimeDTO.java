package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.MovieTimeInfoResponse;
import com.ticketing.server.movie.domain.MovieTime;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredMovieTimeDTO {

	private final Long movieTimeId;

	private final Integer theaterNumber;

	private final Integer round;

	private final LocalDateTime startAt;

	private final LocalDateTime endAt;

	public RegisteredMovieTimeDTO(MovieTime movieTime) {
		this(
			movieTime.getId(),
			movieTime.getTheater().getTheaterNumber(),
			movieTime.getRound(),
			movieTime.getStartAt(),
			movieTime.getEndAt()
		);
	}

	public MovieTimeInfoResponse toResponse() {
		return new MovieTimeInfoResponse(movieTimeId, theaterNumber, round, startAt, endAt);
	}

}
