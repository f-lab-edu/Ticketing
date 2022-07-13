package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.MovieInfoResponse;
import com.ticketing.server.movie.domain.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredMovieDTO {

	private final Long id;

	private final String title;

	public RegisteredMovieDTO(Movie movie) {
		this(
			movie.getId(),
			movie.getTitle()
		);
	}

	public MovieInfoResponse toResponse() {
		return new MovieInfoResponse(id, title);
	}

}
