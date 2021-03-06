package com.ticketing.server.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Theater;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.domain.repository.TheaterRepository;
import com.ticketing.server.movie.service.dto.MovieTimeDTO;
import com.ticketing.server.movie.service.dto.MovieTimeRegisterDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieTimeDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class MovieTimeServiceImplTest {

    String title = "범죄도시2";
	LocalDateTime startAt = LocalDateTime.now();
    List<MovieTime> movieTimes = new ArrayList<>();

    @Mock
    MovieRepository movieRepository;

	@Mock
	TheaterRepository theaterRepository;

    @Mock
    MovieTimeRepository movieTimeRepository;

    @InjectMocks
    MovieTimeServiceImpl movieTimeService;

    @Test
    @DisplayName("MovieTime Service Test - get empty list when there is no valid movie time")
    void shouldGetEmptyList() {
        // given
        Movie movie = new Movie(title, 106L);

        when(movieRepository.findByIdAndDeletedAtNull(any()))
            .thenReturn(Optional.of(movie));

        when(movieTimeRepository.findValidMovieTimes(any(), any(), any()))
            .thenReturn(Collections.emptyList());

        // when
        List<MovieTimeDTO> movieTimeDtos = movieTimeService.getMovieTimes(any(), LocalDate.now());

        // then
        assertTrue(movieTimeDtos.isEmpty());
    }

    @Test
    @DisplayName("MovieTime Service Test - get list when there are valid movie times")
    void shouldGetMovieTimeList() {
        // given
        Movie movie = new Movie(title, 106L);
        Theater theater = new Theater(1);
        MovieTime movieTime = new MovieTime(movie, theater, 1,
            LocalDateTime.of(2022, 7, 1, 17, 0, 0)
        );

        movieTimes.add(movieTime);

        when(movieRepository.findByIdAndDeletedAtNull(any()))
            .thenReturn(Optional.of(movie));

        when(movieTimeRepository.findValidMovieTimes(any(), any(), any()))
            .thenReturn(movieTimes);

        // when
	    List<MovieTimeDTO> movieTimeDtos = movieTimeService.getMovieTimes(any(), LocalDate.of(2022, 07, 01));

        // then
        assertTrue(!movieTimeDtos.isEmpty());
    }

	@Test
	@DisplayName("MovieTime Service Test - register movie time")
	void shouldAbleToRegisterMovieTime() {
		// given
		Movie movie = new Movie(title, 100L);
		Theater theater = new Theater(1);
		MovieTime movieTime = new MovieTime(movie, theater, 1, startAt);

		when(movieRepository.findByIdAndDeletedAtNull(anyLong()))
			.thenReturn(Optional.of(movie));

		when(theaterRepository.findByTheaterNumber(anyInt()))
			.thenReturn(Optional.of(theater));

		when(movieTimeRepository.findByMovieAndTheaterAndRoundAndDeletedAtNull(any(), any(), anyInt()))
			.thenReturn(Optional.empty());

		when(movieTimeRepository.save(any()))
			.thenReturn(movieTime);

		// when
		RegisteredMovieTimeDTO registeredMovieTimeDto =
			movieTimeService.registerMovieTime(
				new MovieTimeRegisterDTO(1L, 1, 1, startAt)
			);

		// then
		assertThat(registeredMovieTimeDto).isNotNull();
		assertTrue(registeredMovieTimeDto.getTheaterNumber() == 1);
		assertTrue(registeredMovieTimeDto.getStartAt() == startAt);
		assertTrue(registeredMovieTimeDto.getRound() == 1);
	}

	@Test
	@DisplayName("MovieTime Service Test - register movie time when there is same movie time already")
	void shouldThrowExceptionWhenRegisteringDuplicateMovieTime() {
		// given
		Movie movie = new Movie(title, 100L);
		Theater theater = new Theater(1);
		MovieTime movieTime = new MovieTime(movie, theater, 1, startAt);
		MovieTimeRegisterDTO movieTimeRegisterDto = new MovieTimeRegisterDTO(1L, 1, 1, startAt);

		when(movieRepository.findByIdAndDeletedAtNull(anyLong()))
			.thenReturn(Optional.of(movie));

		when(theaterRepository.findByTheaterNumber(anyInt()))
			.thenReturn(Optional.of(theater));

		when(movieTimeRepository.findByMovieAndTheaterAndRoundAndDeletedAtNull(any(), any(), anyInt()))
			.thenReturn(Optional.of(movieTime));

		// when
		// then
		assertThatThrownBy(() -> movieTimeService.registerMovieTime(movieTimeRegisterDto))
			.isInstanceOf(TicketingException.class);
	}

	@Test
	@DisplayName("MovieTime Service Test - register movie time when there is no such movie")
	void shouldThrowExceptionWhenRegisteringMovieTimeWithNoSuchMovie() {
		// given
		Theater theater = new Theater(1);
		MovieTimeRegisterDTO movieTimeRegisterDto = new MovieTimeRegisterDTO(1L, 1, 1, startAt);

		when(movieRepository.findByIdAndDeletedAtNull(1L))
			.thenReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> movieTimeService.registerMovieTime(movieTimeRegisterDto))
			.isInstanceOf(TicketingException.class);
	}
}
