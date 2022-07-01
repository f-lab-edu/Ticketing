package com.ticketing.server.movie.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.service.dto.MovieDto;
import com.ticketing.server.movie.service.dto.MovieTimeDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieTimeServiceImplTest {

    LocalDateTime startOfDay = LocalDate.now().atStartOfDay().plusHours(6);
    LocalDateTime endOfDay = startOfDay.plusDays(1);

    @Mock
    MovieRepository movieRepository;

    @Mock
    MovieTimeRepository movieTimeRepository;

    @InjectMocks
    MovieTimeServiceImpl movieTimeService;

    @Test
    @DisplayName("MovieTime Service Test - get movie times when there is no valid movie times")
    void shouldGetEmptyList() {
        // given
        long movieId = 1L;

        when(movieTimeRepository.findValidMovieTimes(movieId, startOfDay, endOfDay))
            .thenReturn(Collections.emptyList());

        // when
        List<MovieTimeDto> movieTimes = movieTimeService.getMovieTimes();

        // then
        assertTrue(movieDtoList.isEmpty());
    }

}
