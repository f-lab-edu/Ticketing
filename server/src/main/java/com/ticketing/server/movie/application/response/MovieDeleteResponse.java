package com.ticketing.server.movie.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieDeleteResponse {

	private final Long id;

	private final String title;

}
