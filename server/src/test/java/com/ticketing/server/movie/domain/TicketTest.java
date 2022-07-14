package com.ticketing.server.movie.domain;

import static com.ticketing.server.movie.domain.MovieTimeTest.MOVIE_TIMES;
import static com.ticketing.server.movie.domain.SeatTest.SEATS_BY_THEATER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

	@Test
	@DisplayName("좌석 예약 성공")
	void makeReservationSuccess() {
		// given
		Ticket ticket = tickets.get(0);

		// when
		ticket = ticket.makeReservation();

		// then
		assertThat(ticket.getStatus()).isEqualTo(TicketStatus.RESERVATION);
	}

	@Test
	@DisplayName("좌석 예약 실패 - 이미 예약중인 좌석인 경우")
	void makeReservationFail_Reservation() {
		// given
		Ticket ticket = tickets.get(0);
		ticket.makeReservation();

		// when
		// then
		assertThatThrownBy(ticket::makeReservation)
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.DUPLICATE_PAYMENT);
	}

	@Test
	@DisplayName("좌석 예약 실패 - 이미 판매된 좌석인 경우")
	void makeReservationFail_Sold() {
		// given
		Ticket ticket = tickets.get(0);
		ticket.makeSold(123L);

		// when
		// then
		assertThatThrownBy(ticket::makeReservation)
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.DUPLICATE_PAYMENT);
	}

	@Test
	@DisplayName("좌석 구매 성공")
	void makeSoldSuccess() {
		// given
		Ticket ticket = tickets.get(0);

		// when
		ticket.makeSold(123L);

		// then
		assertAll(
			() -> assertThat(ticket.getStatus()).isEqualTo(TicketStatus.SOLD),
			() -> assertThat(ticket.getPaymentId()).isEqualTo(123L)
		);
	}

	@Test
	@DisplayName("좌석 구매 실패 - 이미 판매된 좌석")
	void makeSoldFail() {
		// given
		Ticket ticket = tickets.get(0);

		// when
		ticket.makeSold(123L);

		// then
		assertThatThrownBy(() -> ticket.makeSold(123L))
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.DUPLICATE_PAYMENT);
	}

	@Test
	@DisplayName("좌석 취소 성공")
	void cancelSuccess() {
		// given
		Ticket ticket = tickets.get(0);

		// when
		ticket.makeReservation();
		ticket.cancel();

		// then
		assertThat(ticket.getStatus()).isEqualTo(TicketStatus.SALE);
	}

	@Test
	@DisplayName("좌석 취소 실패")
	void cancelFail() {
		// given
		Ticket ticket = tickets.get(0);

		// when
		ticket.makeSold(123L);

		// then
		assertThatThrownBy(() -> ticket.cancel())
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.BAD_REQUEST_PAYMENT_CANCEL);
	}

}
