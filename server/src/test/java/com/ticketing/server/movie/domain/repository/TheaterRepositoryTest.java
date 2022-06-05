package com.ticketing.server.movie.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ticketing.server.movie.domain.Theater;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class TheaterRepositoryTest {

    @Autowired
    TheaterRepository theaterRepository;

    @Test
    @DisplayName("Theater Repository Test - saving theater")
    void ShouldAbleToSaveTheater() {
        // given
        Theater theater = new Theater(1, 100);

        // when
        Theater savedTheater = theaterRepository.save(theater);

        // then
        assertEquals(theater.getTheaterNumber(), savedTheater.getTheaterNumber());
    }

}
