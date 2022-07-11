package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theater extends AbstractEntity {

	@NotNull
	private Integer theaterNumber;

	@OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
	private List<Seat> seats = new ArrayList<>();

	Theater(Long id, Integer theaterNumber) {
		this.id = id;
		this.theaterNumber = theaterNumber;
	}

	public Theater(Integer theaterNumber) {
		this.theaterNumber = theaterNumber;
	}

	public void addSeat(Seat seat) {
		seats.add(seat);
	}

}
