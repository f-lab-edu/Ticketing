package com.ticketing.server.payment.application.response;

import com.ticketing.server.movie.domain.Ticket;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDetailDTO {

	private Integer price;

	private Integer theaterNumber;

	private Integer column;

	private Integer row;

	private LocalDateTime startAt;

	private LocalDateTime endAt;

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
