package com.ticketing.server.movie.application.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketCancelResponse {

	private final List<Long> ticketIds;

}
