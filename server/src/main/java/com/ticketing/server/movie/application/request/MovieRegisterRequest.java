package com.ticketing.server.movie.application.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRegisterRequest {

	@NotEmpty(message = "{validation.not.empty.title}")
	private String title;

	@NotNull(message = "{validation.not.null.runningTime}")
	private Long runningTime;

}
