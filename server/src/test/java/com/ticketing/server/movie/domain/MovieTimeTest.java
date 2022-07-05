package com.ticketing.server.movie.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MovieTimeTest {

	@ParameterizedTest
	@DisplayName("영화상영시간 생성")
	@ValueSource(longs = {130L, 140L, 30L})
	void createMovieTime(Long runningTime) {
		// given
		Movie movie = new Movie("범죄도시2", runningTime);
		Theater theater = new Theater(1);
		LocalDateTime startAt = LocalDateTime.of(2022, 7, 4, 8, 10);

		// when
		MovieTime movieTime = MovieTime.of(movie, theater, 1, startAt);

		// then
		assertThat(movieTime.getEndAt()).isEqualTo(startAt.plusMinutes(runningTime));
	}

}
