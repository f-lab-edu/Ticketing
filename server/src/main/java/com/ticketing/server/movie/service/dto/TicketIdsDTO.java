package com.ticketing.server.movie.service.dto;

import static com.ticketing.server.movie.domain.TicketLock.LOCK_KEY;

import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketIdsDTO {

	@NotEmptyCollection
	private List<Long> ticketIds;

	public List<String> makeTicketLockIds() {
		return ticketIds.stream()
			.map(id -> LOCK_KEY.getValue() + ":" + id)
			.collect(Collectors.toList());
	}

}
