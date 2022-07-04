package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.payment.domain.Payment;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Ticket extends AbstractEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "seat_id", referencedColumnName = "id", updatable = false)
	private Seat seat;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "movie_times_id", referencedColumnName = "id", updatable = false)
	private MovieTime movieTime;

	@ManyToOne
	@JoinColumn(name = "payment_id", referencedColumnName = "id", updatable = false)
	private Payment payment;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@NotNull
	private Integer ticketPrice;

	private Ticket(Seat seat, MovieTime movieTime, Integer ticketPrice) {
		this.seat = seat;
		this.movieTime = movieTime;
		this.ticketPrice = ticketPrice;
		this.status = TicketStatus.SALE;
	}

	public static Ticket of(Seat seat, MovieTime movieTime, Integer ticketPrice) {
		return new Ticket(seat, movieTime, ticketPrice);
	}

}
