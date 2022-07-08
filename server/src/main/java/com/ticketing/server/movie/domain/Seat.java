package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Seat extends AbstractEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "theater_id", referencedColumnName = "id", updatable = false)
	private Theater theater;

	@NotNull
	private Integer seatRow;

	@NotNull
	private Integer seatColumn;

	public Seat(Integer seatRow, Integer seatColumn, Theater theater) {
		this.seatRow = seatRow;
		this.seatColumn = seatColumn;
		setTheater(theater);
	}

	private void setTheater(Theater theater) {
		this.theater = theater;
		theater.addSeat(this);
	}
}
