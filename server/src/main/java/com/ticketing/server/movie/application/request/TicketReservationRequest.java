package com.ticketing.server.movie.application.request;

import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketReservationRequest {

	@NotEmptyCollection
	private List<Long> ticketIds;

}
