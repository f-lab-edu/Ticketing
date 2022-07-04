package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTimeRepository extends JpaRepository<MovieTime, Long> {

    @Query(value = "SELECT mt "
        + "FROM MovieTime mt "
        + "JOIN FETCH mt.movie "
        + "WHERE mt.movie = :movie "
        + "AND mt.startAt BETWEEN :startOfDay AND :endOfDay ")
    List<MovieTime> findValidMovieTimes(Movie movie, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
