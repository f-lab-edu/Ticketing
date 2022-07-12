package com.ticketing.server.payment.service.dto;

import com.ticketing.server.movie.domain.Ticket;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDetailDTO {

	private final Integer price;
	private final Integer theaterNumber;
	private final Integer column;
	private final Integer row;
	private final LocalDateTime startAt;
	private final LocalDateTime endAt;

	public TicketDetailDTO(Ticket ticket) {
		this(
			ticket.getTicketPrice(),
			ticket.getTheaterNumber(),
			ticket.getColumn(),
			ticket.getRow(),
			ticket.getStartAt(),
			ticket.getEndAt()
		);
	}

}
