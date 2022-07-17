package com.ticketing.server.movie.setup;

import com.ticketing.server.global.redis.PaymentCache;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Seat;
import com.ticketing.server.movie.domain.Theater;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.domain.repository.TheaterRepository;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.payment.domain.Payment;
import com.ticketing.server.payment.domain.PaymentStatus;
import com.ticketing.server.payment.domain.PaymentType;
import com.ticketing.server.payment.domain.repository.PaymentRepository;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class
MovieSetupService {

	private final UserRepository userRepository;
	private final MovieRepository movieRepository;
	private final MovieTimeRepository movieTimeRepository;
	private final TheaterRepository theaterRepository;
	private final TicketRepository ticketRepository;
	private final PaymentRepository paymentRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void init() {
		initMovie();
		initTheater();
		initMovieTime();
		initTicket();
//		initPayment();
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
			for (int col = 1; col <= 2; col++) {
				for (int row = 1; row <= 10; row++) {
					new Seat(col, row, theater);
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
			movieTimes.add(new MovieTime(movies.get(0), theater, 1, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 8, 0)));
			movieTimes.add(new MovieTime(movies.get(0), theater, 3, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 0)));
			movieTimes.add(new MovieTime(movies.get(1), theater, 2, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 10, 0)));
			movieTimes.add(new MovieTime(movies.get(2), theater, 4, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 14, 0)));
			movieTimes.add(new MovieTime(movies.get(0), theater, 5, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 16, 0)));
			movieTimes.add(new MovieTime(movies.get(3), theater, 6, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 18, 0)));
			movieTimes.add(new MovieTime(movies.get(0), theater, 7, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 21, 0)));
			movieTimes.add(new MovieTime(movies.get(4), theater, 8, LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 23, 0)));
		}

		movieTimeRepository.saveAll(movieTimes);
	}

	private void initTicket() {
		List<MovieTime> movieTimes = movieTimeRepository.findAll();

		List<Ticket> tickets = new ArrayList<>();
		Integer ticketPrice = 15_000;
		for (MovieTime movieTime : movieTimes) {
			for (Seat seat : movieTime.getSeats()) {
				tickets.add(new Ticket(seat, movieTime, ticketPrice));
			}
		}

		ticketRepository.saveAll(tickets);
	}

	private void initPayment() {
		User user = userRepository.save(new User(123L, "김동효", "kdhyo98@gmail.com", passwordEncoder.encode("123123"), UserGrade.USER, "010-1234-5678"));

		List<Ticket> tickets = ticketRepository.findAll();
		Ticket ticket = tickets.get(0);
		String title = ticket.getMovieTime().getMovie().getTitle();
		PaymentCache paymentCache = new PaymentCache(
			user.getEmail(),
			title,
			"T2d03c9130bf237a9700",
			List.of(1L, 2L),
			user.getAlternateId(),
			124124231513245L,
			30_000
		);

		Payment payment = new Payment(paymentCache, PaymentType.KAKAO_PAY, PaymentStatus.SOLD, 30_000);

		paymentRepository.save(payment);

		ticket.registerPayment(payment.getId());
		tickets.get(1).registerPayment(payment.getId());
	}

}
