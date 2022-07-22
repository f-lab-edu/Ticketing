package com.ticketing.server.movie.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketLock {

	LOCK_KEY("TicketLock"),
	LOCK_VALUE("lock");

	private final String value;

}
