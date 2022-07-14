package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.application.response.TicketSoldResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketsSoldDTO {

	private final Long paymentId;
	private final List<TicketSoldDTO> soldDtoList;

	public TicketSoldResponse toResponse() {
		return new TicketSoldResponse(paymentId, soldDtoList);
	}

}
