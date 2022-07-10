package com.ticketing.server.movie.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.application.request.MovieRegisterRequest;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.service.dto.MovieDTO;
import com.ticketing.server.movie.service.dto.MovieRegisterDTO;
import com.ticketing.server.movie.service.interfaces.MovieService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;

	public MovieDTO registerMovie(MovieRegisterDTO movieRegisterDto) {
		Optional<Movie> movie = movieRepository.findByTitle(movieRegisterDto.getTitle());

		if(movie.isEmpty()) {
			return movieRepository.save(movieRegisterDto.toMovie());
		}

		throw ErrorCode.throwDuplicateMovie();
	}

	public List<MovieDTO> getMovies() {
		List<Movie> movies = movieRepository.findValidMovies();

		return movies.stream()
			.map(MovieDTO::from)
			.collect(Collectors.toList());

	}

}
