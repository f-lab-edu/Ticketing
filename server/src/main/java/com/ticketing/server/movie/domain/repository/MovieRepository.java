package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.Movie;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);

    List<Movie> findByDeletedAt(LocalDateTime localDateTime);

}
