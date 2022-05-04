package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.MovieTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTimesRepository extends JpaRepository<MovieTimes, Long> {

}
