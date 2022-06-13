package com.ticketing.server.movie.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.service.dto.MovieDto;
import java.util.ArrayList;
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

    @BeforeEach
    void init() {
        movie = new Movie("범죄도시2", 106);
        movieDto = movie.toDto();

        movies.add(movie);
        movieDtos.add(movieDto);
    }

    @Test
    @DisplayName("Movie Service Test - get movies")
    void shouldAbleToGetMovies() {
        // given
        when(movieRepository.findByDeletedAt(null)).thenReturn(movies);

        // when
        List<MovieDto> movieDtoList = movieService.getMovies();

        // then
        assertFalse(movieDtoList.isEmpty());
    }

}
