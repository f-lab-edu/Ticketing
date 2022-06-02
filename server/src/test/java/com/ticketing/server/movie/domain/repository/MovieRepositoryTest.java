package com.ticketing.server.movie.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ticketing.server.movie.domain.Movie;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Order(1)
    @Test
    @Rollback(value = false)
    @DisplayName("Movie Repository - test saving movie")
    void shouldAbleToSaveMovie() {
        // given
        Movie movie = new Movie("범죄도시 2", 106);

        // when
        Movie savedMovie = movieRepository.save(movie);

        // then
        assertEquals(movie.getTitle(), savedMovie.getTitle());
    }

    @Order(2)
    @Test
    @DisplayName("Movie Repository Test - finding movie with title")
    void ShouldAbleToFindMovieWithTitle() {
        // given, when
        Optional<Movie> optionalMovie = movieRepository.findByTitle("범죄도시 2");

        // then
        assertTrue(optionalMovie.isPresent());
    }

    @Order(3)
    @Test
    @DisplayName("Movie Repository Test - finding movie that doesn't exist")
    void ShouldNotAbleToFindMovie() {
        // given, when
        Optional<Movie> optionalMovie = movieRepository.findByTitle("존재하지 않는 영화");

        // then
        assertFalse(optionalMovie.isPresent());
    }
}
