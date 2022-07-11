package com.ticketing.server.movie.domain;

import static com.ticketing.server.movie.domain.MovieTest.MOVIES;
import static com.ticketing.server.movie.domain.TheaterTest.THEATERS;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MovieTimeTest {

	public static final List<MovieTime> MOVIE_TIMES;

	static {
		MOVIE_TIMES = new ArrayList<>();

		LocalDateTime now = LocalDateTime.now();
		List<LocalDateTime> startAts = Arrays.asList(
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 0),
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 0),
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 0),
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 14, 0),
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 0),
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 18, 0),
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 21, 0),
			LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 23, 0)
		);

		Long idx = 1L;
		for (Theater theater : THEATERS) {
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(0), theater, 1, startAts.get(0)));
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(0), theater, 3, startAts.get(1)));
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(1), theater, 2, startAts.get(2)));
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(2), theater, 4, startAts.get(3)));
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(0), theater, 5, startAts.get(4)));
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(3), theater, 6, startAts.get(5)));
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(0), theater, 7, startAts.get(6)));
			MOVIE_TIMES.add(new MovieTime(idx++, MOVIES.get(4), theater, 8, startAts.get(7)));
		}
	}

	@ParameterizedTest
	@DisplayName("영화상영시간 생성")
	@ValueSource(longs = {130L, 140L, 30L})
	void createMovieTime(Long runningTime) {
		// given
		Movie movie = new Movie("범죄도시2", runningTime);
		Theater theater = new Theater(1);
		LocalDateTime startAt = LocalDateTime.of(2022, 7, 4, 8, 10);

		// when
		MovieTime movieTime = new MovieTime(movie, theater, 1, startAt);

		// then
		assertThat(movieTime.getEndAt()).isEqualTo(startAt.plusMinutes(runningTime));
	}

}
