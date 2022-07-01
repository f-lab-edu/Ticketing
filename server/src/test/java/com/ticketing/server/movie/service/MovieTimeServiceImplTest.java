package com.ticketing.server.movie.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.service.dto.MovieTimeDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

@ExtendWith(MockitoExtension.class)
public class MovieTimeServiceImplTest {

    LocalDateTime startOfDay = LocalDate.now().atStartOfDay().plusHours(6);
    LocalDateTime endOfDay = startOfDay.plusDays(1);

    @Mock
    MovieRepository movieRepository;

    @Mock
    MovieTimeRepository movieTimeRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @InjectMocks
    MovieTimeServiceImpl movieTimeService;

    @Test
    @Transactional
    @DisplayName("MovieTime Service Test - get empty list when there is no valid movie times")
    void shouldGetEmptyList() {
        // given
        String title = "범죄도시2";
        Movie movie = new Movie(title, 106);
        when(movieRepository.findByTitle(title)).thenReturn(Optional.of(movie));

        when(movieTimeRepository.findValidMovieTimes(1L, startOfDay, endOfDay))
            .thenReturn(Collections.emptyList());

        // when
        Optional<Movie> result = movieRepository.findByTitle(title);
        List<MovieTimeDto> movieTimeDtoList = movieTimeService.getMovieTimes(title, LocalDate.now());

        // then
        assertTrue(movieTimeDtoList.isEmpty());
    }

}
