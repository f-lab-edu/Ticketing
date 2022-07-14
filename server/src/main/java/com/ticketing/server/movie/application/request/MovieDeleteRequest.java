package com.ticketing.server.movie.application.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDeleteRequest {

	@NotNull(message = "{validation.not.null.id}")
	private Long id;

}
