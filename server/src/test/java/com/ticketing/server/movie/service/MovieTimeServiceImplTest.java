package com.ticketing.server.movie.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Theater;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.service.dto.MovieTimeListDTO;
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
    List<MovieTime> movieTimes = new ArrayList<>();

    @Mock
    MovieRepository movieRepository;

    @Mock
    MovieTimeRepository movieTimeRepository;

    @InjectMocks
    MovieTimeServiceImpl movieTimeService;

    @Test
    @DisplayName("MovieTime Service Test - get empty list when there are no valid movie times")
    void shouldGetEmptyList() {
        // given
        Movie movie = new Movie(title, 106L);

        when(movieRepository.findByIdAndDeletedAtNull(any()))
            .thenReturn(Optional.of(movie));

        when(movieTimeRepository.findValidMovieTimes(any(), any(), any()))
            .thenReturn(Collections.emptyList());

        // when
        MovieTimeListDTO movieTimeListDto = movieTimeService.getMovieTimes(any(), LocalDate.now());

        // then
        assertTrue(movieTimeListDto.getMovieTimeDtos().isEmpty());
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
	    MovieTimeListDTO movieTimeListDto = movieTimeService.getMovieTimes(any(), LocalDate.of(2022, 07, 01));

        // then
        assertTrue(!movieTimeListDto.getMovieTimeDtos().isEmpty());
    }

}
