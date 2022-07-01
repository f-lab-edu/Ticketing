package com.ticketing.server.movie.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.service.dto.MovieDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    Movie movie;
    MovieDto movieDto;
    List<Movie> movies = new ArrayList<>();
    List<MovieDto> movieDtos = new ArrayList<>();

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @Test
    @DisplayName("Movie Service Test - get movies when there is no movie")
    void shouldGetEmptyList() {
        // given
        when(movieRepository.findValidMovies()).thenReturn(Collections.emptyList());

        // when
        List<MovieDto> movieDtoList = movieService.getMovies();

        // then
        assertTrue(movieDtoList.isEmpty());
    }

    @Test
    @DisplayName("Movie Service Test - get movies")
    void shouldAbleToGetMovies() {
        // given
        movie = new Movie("범죄도시2", 106);
        movieDto = movie.toDto();
        movies.add(movie);
        movieDtos.add(movieDto);

        when(movieRepository.findValidMovies()).thenReturn(movies);

        // when
        List<MovieDto> movieDtoList = movieService.getMovies();

        // then
        assertTrue(!movieDtoList.isEmpty());
    }

}
