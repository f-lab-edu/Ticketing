package com.ticketing.server.movie.domain;

import static com.ticketing.server.movie.domain.TheaterTest.THEATERS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SeatTest {

	public static final Map<Theater, List<Seat>> SEATS_BY_THEATER = new HashMap<>();

	static {
		Long idx = 1L;
		for (Theater theater : THEATERS) {
			List<Seat> seats = new ArrayList<>();
			for (int col = 1; col <= 2; col++) {
				for (int row = 1; row <= 10; row++) {
					seats.add(new Seat(idx++, col, row, theater));
				}
			}
			SEATS_BY_THEATER.put(theater, seats);
		}
	}

}
