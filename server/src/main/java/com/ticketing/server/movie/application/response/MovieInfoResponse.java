package com.ticketing.server.movie.application.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieInfoResponse {

	@ApiModelProperty(value = "영화 ID")
	private Long movieId;

	@ApiModelProperty(value = "영화 제목")
	private String title;

}
