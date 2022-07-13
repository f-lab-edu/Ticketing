package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.MovieDeleteResponse;
import com.ticketing.server.movie.domain.Movie;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeletedMovieDTO {

	private final Long id;

	private final String title;

	private final LocalDateTime deletedAt;

	public DeletedMovieDTO(Movie movie) {
		this(
			movie.getId(),
			movie.getTitle(),
			movie.getDeletedAt()
		);
	}

	public MovieDeleteResponse toResponse() {
		return new MovieDeleteResponse(id, title, deletedAt);
	}

}
