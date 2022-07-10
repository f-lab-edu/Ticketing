package com.ticketing.server.movie.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.service.dto.MovieTimeDTO;
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
	public List<MovieTimeDTO> getMovieTimes(String title, LocalDate runningDate) {
		Movie movie = movieRepository.findByTitle(title)
			.orElseThrow(ErrorCode::throwMovieNotFound);

		LocalDateTime startOfDay = runningDate.atStartOfDay().plusHours(6);
		LocalDateTime endOfDay = startOfDay.plusDays(1);

		List<MovieTime> movieTimes = movieTimeRepository.findValidMovieTimes(movie, startOfDay, endOfDay);

		return movieTimes.stream()
			.map(MovieTimeDTO::from)
			.collect(Collectors.toList());

	}

}
