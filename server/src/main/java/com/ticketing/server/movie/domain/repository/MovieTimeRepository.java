package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.MovieTime;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTimeRepository extends JpaRepository<MovieTime, Long> {

    @Query(value = "SELECT * "
        + "FROM movie_time "
        + "WHERE movie_id = :movieId "
        + "AND start_at "
        + "BETWEEN date_format(start_at, :startOfDay) "
        + "AND date_format(start_at, :endOfDay)", nativeQuery = true)
    List<MovieTime> findValidMovieTimes(long movieId, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
