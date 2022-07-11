package com.ticketing.server.movie.domain;

import static com.ticketing.server.movie.domain.MovieTimeTest.MOVIE_TIMES;
import static com.ticketing.server.movie.domain.SeatTest.SEATS_BY_THEATER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.exception.TicketingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TicketTest {

	List<Ticket> tickets;

	@BeforeEach
	void init() {
		tickets = setupTickets();
	}

	public static List<Ticket> setupTickets() {
		List<Ticket> tickets = new ArrayList<>();
		Integer ticketPrice = 15_000;

		Long id = 1L;
		for (MovieTime movieTime : MOVIE_TIMES) {
			List<Seat> seats = SEATS_BY_THEATER.get(movieTime.getTheater());
			for (Seat seat : seats) {
				tickets.add(new Ticket(id++, seat, movieTime, ticketPrice));
			}
		}
		return tickets;
	}

	@Test
	@DisplayName("티켓에 결제 ID 등록 성공")
	void registerPaymentSuccess() {
		// given
		Ticket ticket = tickets.get(0);

		// when
		ticket.registerPayment(1L);

		// then
		assertThat(ticket.getPaymentId()).isEqualTo(1L);
	}

	@Test
	@DisplayName("티켓에 결제 ID 등록 실패 - 이미 등록된 경우")
	void registerPaymentFail_Duplicate() {
		// given
		Ticket ticket = tickets.get(0);

		// when
		ticket.registerPayment(1L);

		// then
		assertThatThrownBy(() -> ticket.registerPayment(1L))
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.DUPLICATE_PAYMENT);
	}

}
