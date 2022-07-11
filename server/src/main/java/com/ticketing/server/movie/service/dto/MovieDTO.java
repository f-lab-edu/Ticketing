package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.domain.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieDTO {

    private String title;

    public static MovieDTO from(Movie movie) {
        return new MovieDTO(movie.getTitle());
    }

}
