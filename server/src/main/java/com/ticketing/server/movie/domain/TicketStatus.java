package com.ticketing.server.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketStatus {

	SALE("판매가능"),
	RESERVATION("예약"),
	SOLD("판매완료");

	private final String name;

}
