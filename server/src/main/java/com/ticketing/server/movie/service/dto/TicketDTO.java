package com.ticketing.server.movie.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketDTO {

	private Long ticketId;

	private Integer ticketPrice;

	private Integer seatRow;

	private Integer seatColumn;

}
