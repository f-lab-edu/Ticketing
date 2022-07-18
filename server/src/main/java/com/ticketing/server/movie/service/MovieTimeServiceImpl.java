package com.ticketing.server.movie.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Theater;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.domain.repository.TheaterRepository;
import com.ticketing.server.movie.service.dto.MovieTimeDTO;
import com.ticketing.server.movie.service.dto.MovieTimeRegisterDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieTimeDTO;
import com.ticketing.server.movie.service.interfaces.MovieTimeService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieTimeServiceImpl implements MovieTimeService {

	private final MovieRepository movieRepository;

	private final TheaterRepository theaterRepository;

	private final MovieTimeRepository movieTimeRepository;

	@Override
	public RegisteredMovieTimeDTO registerMovieTime(@Valid MovieTimeRegisterDTO movieTimeRegisterDto) {
		Movie movie = findMovieById(movieTimeRegisterDto.getMovieId());
		Theater theater = findTheaterByNumber(movieTimeRegisterDto.getTheaterNumber());

		int round = movieTimeRegisterDto.getRound();

		Optional<MovieTime> movieTime =
			movieTimeRepository.findByMovieAndTheaterAndRoundAndDeletedAtNull(movie, theater, round);

		if (movieTime.isEmpty()) {
			MovieTime newMovieTime = movieTimeRepository.save(
				new MovieTime(movie, theater, round, movieTimeRegisterDto.getStartAt())
			);

			return new RegisteredMovieTimeDTO(newMovieTime);
		}

		throw ErrorCode.throwDuplicateMovieTime();
	}

	@Override
	public List<MovieTimeDTO> getMovieTimes(Long movieId, LocalDate runningDate) {
		Movie movie = findMovieById(movieId);

		LocalDateTime startOfDay = runningDate.atStartOfDay().plusHours(6);
		LocalDateTime endOfDay = startOfDay.plusDays(1);

		List<MovieTime> movieTimes = movieTimeRepository.findValidMovieTimes(movie, startOfDay, endOfDay);

		return movieTimes.stream()
			.map(movieTime -> movieTime.toMovieTimeDTO())
			.collect(Collectors.toList());
	}

	@Override
	public Movie findMovieById(Long movieId) {
		Movie movie = movieRepository.findByIdAndDeletedAtNull(movieId)
			.orElseThrow(ErrorCode::throwMovieNotFound);

		return movie;
	}

	@Override
	public Theater findTheaterByNumber(Integer theaterNumber) {
		Theater theater = theaterRepository.findByTheaterNumber(theaterNumber)
			.orElseThrow(ErrorCode::throwTheaterNotFound);

		return theater;
	}

}
