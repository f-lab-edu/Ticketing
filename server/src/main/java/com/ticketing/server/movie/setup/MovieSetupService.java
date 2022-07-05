package com.ticketing.server.movie.setup;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Seat;
import com.ticketing.server.movie.domain.Theater;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.domain.repository.TheaterRepository;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class
MovieSetupService {

	private final MovieRepository movieRepository;
	private final MovieTimeRepository movieTimeRepository;
	private final TheaterRepository theaterRepository;
	private final TicketRepository ticketRepository;

	@Transactional
	public void init() {
		initMovie();
		initTheater();
		initMovieTime();
		initTicket();
	}

	private void initMovie() {
		List<Movie> movies = Arrays.asList(
			new Movie("탑건: 매버릭", 130L),
			new Movie("헤어질 결심", 138L),
			new Movie("마녀2", 137L),
			new Movie("범죄도시2", 106L),
			new Movie("버즈 라이트이어", 105L)
		);

		movieRepository.saveAll(movies);
	}

	private void initTheater() {
		List<Theater> theaters = Arrays.asList(
			new Theater(1),
			new Theater(2)
		);

		for (Theater theater : theaters) {
			for (int row = 1; row <= 2; row++) {
				for (int col = 1; col <= 10; col++) {
					new Seat(row, col, theater);
				}
			}
		}

		theaterRepository.saveAll(theaters);
	}

	private void initMovieTime() {
		List<Movie> movies = movieRepository.findAll();
		List<Theater> theaters = theaterRepository.findAll();

		LocalDateTime now = LocalDateTime.now();

		List<MovieTime> movieTimes = new ArrayList<>();
		for (Theater theater : theaters) {
			movieTimes.add(MovieTime.of(movies.get(0), theater, 1, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 0)));
			movieTimes.add(MovieTime.of(movies.get(0), theater, 3, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 0)));
			movieTimes.add(MovieTime.of(movies.get(1), theater, 2, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 0)));
			movieTimes.add(MovieTime.of(movies.get(2), theater, 4, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 14, 0)));
			movieTimes.add(MovieTime.of(movies.get(0), theater, 5, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 0)));
			movieTimes.add(MovieTime.of(movies.get(3), theater, 6, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 18, 0)));
			movieTimes.add(MovieTime.of(movies.get(0), theater, 7, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 21, 0)));
			movieTimes.add(MovieTime.of(movies.get(4), theater, 8, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 23, 0)));
		}

		movieTimeRepository.saveAll(movieTimes);
	}

	private void initTicket() {
		List<MovieTime> movieTimes = movieTimeRepository.findAll();

		List<Ticket> tickets = new ArrayList<>();
		Integer ticketPrice = 15_000;
		for (MovieTime movieTime : movieTimes) {
			for (Seat seat : movieTime.getSeats()) {
				tickets.add(Ticket.of(seat, movieTime, ticketPrice));
			}
		}

		ticketRepository.saveAll(tickets);
	}

}
