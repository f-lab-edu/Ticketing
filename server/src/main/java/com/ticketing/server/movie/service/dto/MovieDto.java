package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.domain.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieDto {

    private String title;

    public static MovieDto from(Movie movie) {
        return new MovieDto(movie.getTitle());
    }

}
