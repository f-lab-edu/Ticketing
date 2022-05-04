package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Seat extends AbstractEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "theater_id", referencedColumnName = "id", updatable = false)
	private Theater theater;

	@NotNull
	private Integer seatColumn;

	@NotNull
	private Integer seatRow;

}
