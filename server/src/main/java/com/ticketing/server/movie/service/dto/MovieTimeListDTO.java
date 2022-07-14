package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.MovieTimeListResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieTimeListDTO {

	private final List<MovieTimeDTO> movieTimeDtos;

	public MovieTimeListResponse toResponse() {
		return new MovieTimeListResponse(movieTimeDtos);
	}

}
