package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import javax.validation.constraints.NotNull;

public interface TicketService {

	TicketDetailsDTO findTicketsByPaymentId(@NotNull Long paymentId);

}
