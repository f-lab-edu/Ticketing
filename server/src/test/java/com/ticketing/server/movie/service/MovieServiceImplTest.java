package com.ticketing.server.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.service.dto.DeletedMovieDTO;
import com.ticketing.server.movie.service.dto.MovieDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    Movie movie;
    List<Movie> movies = new ArrayList<>();

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @Test
    @DisplayName("Movie Service Test - get movies when there is no movie")
    void shouldGetEmptyList() {
        // given
        when(movieRepository.findValidMovies())
            .thenReturn(Collections.emptyList());

        // when
        List<MovieDTO> movieDtos = movieService.getMovies();

        // then
        assertTrue(movieDtos.isEmpty());
    }

    @Test
    @DisplayName("Movie Service Test - get movies")
    void shouldAbleToGetMovies() {
        // given
        movie = new Movie("범죄도시2", 106L);
        movies.add(movie);

        when(movieRepository.findValidMovies())
            .thenReturn(movies);

	    // when
	    List<MovieDTO> movieDtos = movieService.getMovies();

	    // then
	    assertTrue(!movieDtos.isEmpty());
    }

	@Test
	@DisplayName("Movie Service Test - register movie")
	void shouldAbleToRegisterMovie() {
		// given
		String title = "추가할 영화 제목";
		movie = new Movie(title, 100L);

		when(movieRepository.findValidMovieWithTitle(title))
			.thenReturn(Optional.empty());
		when(movieRepository.save(any()))
			.thenReturn(movie);

		// when
		RegisteredMovieDTO registeredMovieDto =
			movieService.registerMovie(title, movie.getRunningTime());

		// then
		assertThat(registeredMovieDto).isNotNull();
		assertTrue(registeredMovieDto.getTitle().equals(title));
	}

	@Test
	@DisplayName("Movie Service Test - delete movie")
	void shouldAbleToDeleteMovie() {
		// given
		Movie movie = new Movie("삭제할 영화 제목", 100L);

		when(movieRepository.findByIdAndDeletedAtNull(1L))
			.thenReturn(Optional.of(movie));

		// when
		DeletedMovieDTO deletedMovieDto =
			movieService.deleteMovie(1L);

		// then
		assertTrue(deletedMovieDto.getTitle().equals("삭제할 영화 제목"));
		assertThat(deletedMovieDto.getDeletedAt()).isNotNull();
	}
	
}
