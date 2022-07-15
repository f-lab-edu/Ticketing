package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.dto.TicketListDTO;
import com.ticketing.server.movie.service.dto.TicketsCancelDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.movie.service.dto.TicketsSoldDTO;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface TicketService {

	TicketListDTO getTickets(@NotNull Long movieTimeId);

	TicketDetailsDTO findTicketsByPaymentId(@NotNull Long paymentId);

	TicketsReservationDTO ticketReservation(@NotEmptyCollection List<Long> ticketIds);

	TicketsSoldDTO ticketSold(@NotNull Long paymentId, @NotEmptyCollection List<Long> ticketIds);

	TicketsCancelDTO ticketCancel(@NotEmptyCollection List<Long> ticketIds);

}
