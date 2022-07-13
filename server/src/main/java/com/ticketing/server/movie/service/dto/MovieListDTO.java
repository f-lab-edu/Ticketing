package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.MovieListResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor()
public class MovieListDTO {

	private final List<MovieDTO> movieDtos;

	public MovieListResponse toResponse() {
		return new MovieListResponse(movieDtos);
	}

}
