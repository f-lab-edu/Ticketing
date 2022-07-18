package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.service.dto.TicketDTO;
import com.ticketing.server.movie.service.dto.TicketRefundDTO;
import com.ticketing.server.movie.service.dto.TicketsCancelDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.movie.service.dto.TicketsSoldDTO;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.util.List;
import java.util.function.UnaryOperator;
import javax.validation.constraints.NotNull;

public interface TicketService {

	List<TicketDTO> getTickets(@NotNull Long movieTimeId);

	List<TicketDetailDTO> findTicketsByPaymentId(@NotNull Long paymentId);

	TicketsReservationDTO ticketReservation(@NotEmptyCollection List<Long> ticketIds);

	TicketsSoldDTO ticketSold(@NotNull Long paymentId, @NotEmptyCollection List<Long> ticketIds);

	TicketsCancelDTO ticketCancel(@NotEmptyCollection List<Long> ticketIds);

	List<TicketRefundDTO> ticketsRefund(@NotNull Long paymentId, UnaryOperator<Ticket> refund);

}
