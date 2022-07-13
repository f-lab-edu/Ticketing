package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.service.dto.MovieDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieInfoResponse {

	@ApiModelProperty(value = "영화 ID")
	private Long movieId;

	@ApiModelProperty(value = "영화 제목")
	private String title;

	public static MovieInfoResponse from(MovieDTO movieDto) {
		return new MovieInfoResponse(movieDto.getMovieId(), movieDto.getTitle());
	}

}
