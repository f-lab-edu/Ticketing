package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Theater extends AbstractEntity {

	@NotNull
	private Integer theaterNumber;

	private Integer seatCount;

}
