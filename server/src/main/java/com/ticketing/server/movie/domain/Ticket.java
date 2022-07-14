package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.global.exception.ErrorCode;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends AbstractEntity {

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id", referencedColumnName = "id", updatable = false)
	private Seat seat;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_times_id", referencedColumnName = "id", updatable = false)
	private MovieTime movieTime;

	private Long paymentId;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@NotNull
	private Integer ticketPrice;

	public Ticket(Seat seat, MovieTime movieTime, Integer ticketPrice) {
		this.seat = seat;
		this.movieTime = movieTime;
		this.ticketPrice = ticketPrice;
		this.status = TicketStatus.SALE;
	}

	Ticket(Long id, Seat seat, MovieTime movieTime, Integer ticketPrice) {
		this.id = id;
		this.seat = seat;
		this.movieTime = movieTime;
		this.ticketPrice = ticketPrice;
		this.status = TicketStatus.SALE;
	}

	public Ticket makeReservation() {
		if (!TicketStatus.SALE.equals(status)) {
			throw ErrorCode.throwDuplicatePayment();
		}

		status = TicketStatus.RESERVATION;
		return this;
	}

	public Ticket makeSold(Long paymentId) {
		if (TicketStatus.SOLD.equals(status)) {
			throw ErrorCode.throwDuplicatePayment();
		}

		status = TicketStatus.SOLD;
		this.paymentId = paymentId;
		return this;
	}

	public Ticket cancel() {
		if (!TicketStatus.RESERVATION.equals(status)) {
			throw ErrorCode.throwBadRequestPaymentCancel();
		}

		status = TicketStatus.SALE;
		return this;
	}

	public Long getMovieTimeId() {
		return movieTime.getId();
	}

	public Integer getColumn() {
		return this.seat.getSeatColumn();
	}

	public Integer getRow() {
		return this.seat.getSeatRow();
	}

	public LocalDateTime getStartAt() {
		return this.movieTime.getStartAt();
	}

	public LocalDateTime getEndAt() {
		return this.movieTime.getEndAt();
	}

	public Integer getTheaterNumber() {
		return this.seat.getTheaterNumber();
	}

	public String getMovieTitle() {
		return this.movieTime.getMovieTitle();
	}

	public void registerPayment(Long id) {
		if (this.paymentId != null) {
			throw ErrorCode.throwDuplicatePayment();
		}

		this.paymentId = id;
	}

}
