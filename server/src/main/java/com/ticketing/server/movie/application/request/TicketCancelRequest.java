package com.ticketing.server.movie.application.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketCancelRequest {

	private final List<Long> ticketIds;

}
