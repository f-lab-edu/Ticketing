package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);

	Optional<Movie> findByIdAndDeletedAtNull(Long id);

	@Query(value = "SELECT m "
		+ "FROM Movie m "
		+ "WHERE m.title = :title "
		+ "AND m.deletedAt IS NULL")
	Optional<Movie> findValidMovieWithTitle(String title);

    @Query(value = "SELECT * "
        + "FROM movie "
        + "WHERE deleted_at IS NULL", nativeQuery = true)
    List<Movie> findValidMovies();

}
