package com.ticketing.server.movie.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.service.dto.MovieTimeDTO;
import com.ticketing.server.movie.service.dto.MovieTimeListDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieTimeDTO;
import com.ticketing.server.movie.service.interfaces.MovieTimeService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieTimeServiceImpl implements MovieTimeService {

	private final MovieRepository movieRepository;

	private final MovieTimeRepository movieTimeRepository;

	@Override
	public RegisteredMovieTimeDTO registerMovieTime() {
		MovieTime movieTime = movieTimeRepository.findByMovieAndTheaterAndRound();
	}

	@Override
	public MovieTimeListDTO getMovieTimes(Long movieId, LocalDate runningDate) {
		Movie movie = movieRepository.findByIdAndDeletedAtNull(movieId)
			.orElseThrow(ErrorCode::throwMovieNotFound);

		LocalDateTime startOfDay = runningDate.atStartOfDay().plusHours(6);
		LocalDateTime endOfDay = startOfDay.plusDays(1);

		List<MovieTime> movieTimes = movieTimeRepository.findValidMovieTimes(movie, startOfDay, endOfDay);

		List<MovieTimeDTO> movieTimeDtos = movieTimes.stream()
			.map(movieTime -> movieTime.toMovieTimeDTO())
			.collect(Collectors.toList());

		return new MovieTimeListDTO(movieTimeDtos);
	}

}
