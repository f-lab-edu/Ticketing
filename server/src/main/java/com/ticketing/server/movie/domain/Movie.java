package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Movie extends AbstractEntity {

	@NotNull
	private String title;

	@NotNull
	private Integer runningTime;

}
