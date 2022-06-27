package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTimeRepository extends JpaRepository<MovieTime, Long> {

    List<MovieTime> findByMovieAndRunningDate(Movie movie, LocalDate localDate);

}
