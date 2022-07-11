package com.ticketing.server.movie.domain;

import java.util.Arrays;
import java.util.List;

class MovieTest {

	public static final List<Movie> MOVIES;

	static {
		MOVIES = Arrays.asList(
			new Movie(1L, "탑건: 매버릭", 130L),
			new Movie(2L, "헤어질 결심", 138L),
			new Movie(3L, "마녀2", 137L),
			new Movie(4L, "범죄도시2", 106L),
			new Movie(5L, "버즈 라이트이어", 105L)
		);
	}

}
