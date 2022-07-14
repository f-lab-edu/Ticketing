package com.ticketing.server.movie.application.request;

import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketSoldRequest {

	@NotNull
	private Long paymentId;

	@NotEmptyCollection
	private List<Long> ticketIds;

}
