package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import javax.persistence.Entity;
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
public class Seat extends AbstractEntity {

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "theater_id", referencedColumnName = "id", updatable = false)
	private Theater theater;

	@NotNull
	private Integer seatColumn;

	@NotNull
	private Integer seatRow;

	public Seat(Integer seatColumn, Integer seatRow, Theater theater) {
		this.seatColumn = seatColumn;
		this.seatRow = seatRow;
		setTheater(theater);
	}

	Seat(Long id, int column, int row, Theater theater) {
		this.id = id;
		this.seatColumn = column;
		this.seatRow = row;
		setTheater(theater);
	}

	private void setTheater(Theater theater) {
		this.theater = theater;
		theater.addSeat(this);
	}

	public Integer getTheaterNumber() {
		return this.theater.getTheaterNumber();
	}

}
