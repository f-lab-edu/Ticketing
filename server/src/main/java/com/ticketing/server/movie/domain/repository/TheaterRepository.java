package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

}
