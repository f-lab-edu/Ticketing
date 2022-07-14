package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.service.dto.TicketSoldDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketSoldResponse {

	private final Long paymentId;
	private final List<TicketSoldDTO> soldDtoList;

}
