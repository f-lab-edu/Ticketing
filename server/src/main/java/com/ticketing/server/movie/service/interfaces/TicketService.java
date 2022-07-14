package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.dto.TicketListDTO;
import javax.validation.constraints.NotNull;

public interface TicketService {

	TicketListDTO getTickets(Long movieTimeId)

	TicketDetailsDTO findTicketsByPaymentId(@NotNull Long paymentId);

}
