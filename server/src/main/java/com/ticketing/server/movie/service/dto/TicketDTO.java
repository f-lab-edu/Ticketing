package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.TicketStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDTO {

	private Long ticketId;

	private Integer ticketPrice;

	private Integer seatRow;

	private Integer seatColumn;

	private TicketStatus status;

	public TicketDTO(Ticket ticket) {
		this(
			ticket.getId(),
			ticket.getTicketPrice(),
			ticket.getRow(),
			ticket.getColumn(),
			ticket.getStatus()
		);
	}

}
