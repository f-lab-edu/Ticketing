package com.ticketing.server.movie.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.service.dto.DeletedMovieDTO;
import com.ticketing.server.movie.service.dto.MovieDTO;
import com.ticketing.server.movie.service.dto.MovieListDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieDTO;
import com.ticketing.server.movie.service.interfaces.MovieService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;

	@Override
	public RegisteredMovieDTO registerMovie(String title, Long runningTime) {
		Optional<Movie> movie = movieRepository.findValidMovieWithTitle(title);

		if(movie.isEmpty()) {
			Movie newMovie = movieRepository.save(
				new Movie(title, runningTime)
			);

			return new RegisteredMovieDTO(newMovie);
		}

		throw ErrorCode.throwDuplicateMovie();
	}

	@Override
	public List<MovieDTO> getMovies() {
		List<Movie> movies = movieRepository.findValidMovies();

		return movies.stream()
			.map(movie -> movie.toMovieDTO())
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public DeletedMovieDTO deleteMovie(Long id) {
		Movie movie = movieRepository.findByIdAndDeletedAtNull(id)
			.orElseThrow(ErrorCode::throwMovieNotFound);

		Movie deletedMovie = movie.delete();

		return new DeletedMovieDTO(deletedMovie);
	}
}
