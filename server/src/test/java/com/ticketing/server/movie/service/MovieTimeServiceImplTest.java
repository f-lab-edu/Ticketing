package com.ticketing.server.movie.service;

import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    MovieTimeRepository movieTimeRepository;

    @InjectMocks
    MovieTimeServiceImpl movieTimeService;

    @Test
    @Rollback
    @DisplayName("MovieTime Service Test - get empty list when there is no valid movie times")
    void shouldGetEmptyList() {

    }

}
