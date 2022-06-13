package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.service.dto.MovieDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieListResponse {

    @ApiModelProperty(value = "영화 제목")
    private List<MovieDto> movieDtos;

    public static MovieListResponse from(List<MovieDto> movieDtos) {
        return new MovieListResponse(movieDtos);
    }

}
