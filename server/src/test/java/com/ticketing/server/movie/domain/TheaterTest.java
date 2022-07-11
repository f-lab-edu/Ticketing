package com.ticketing.server.movie.domain;

import java.util.Arrays;
import java.util.List;

class TheaterTest {

	public static final List<Theater> THEATERS;

	static {
		THEATERS = Arrays.asList(
			new Theater(1L, 1),
			new Theater(2L, 2),
			new Theater(3L, 3),
			new Theater(4L, 4)
		);
	}

}
