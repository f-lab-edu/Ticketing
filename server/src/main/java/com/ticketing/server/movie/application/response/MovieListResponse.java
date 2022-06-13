package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.domain.Movie;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieListResponse {

    @ApiModelProperty(value = "영화 제목")
    private List<Movie> movies;

    public static MovieListResponse from(List<Movie> movies) {
        return new MovieListResponse(movies);
    }

}
