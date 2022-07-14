package com.ticketing.server.movie.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.movie.service.dto.TicketDTO;
import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.dto.TicketListDTO;
import com.ticketing.server.movie.service.interfaces.TicketService;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
@Slf4j
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;

	private final MovieTimeRepository movieTimeRepository;

	@Override
	public TicketListDTO getTickets(@NotNull Long movieTimeId) {
		MovieTime movieTime = movieTimeRepository.findById(movieTimeId)
			.orElseThrow(ErrorCode::throwMovieTimeNotFound);

		List<TicketDTO> tickets = ticketRepository.findValidTickets(movieTimeId)
			.stream()
			.map(TicketDTO::new)
			.collect(Collectors.toList());

		return new TicketListDTO(tickets);
	}

	@Override
	public TicketDetailsDTO findTicketsByPaymentId(@NotNull Long paymentId) {
		List<TicketDetailDTO> ticketDetails = ticketRepository.findTicketFetchJoinByPaymentId(paymentId)
			.stream()
			.map(TicketDetailDTO::new)
			.collect(Collectors.toList());

		if (ticketDetails.isEmpty()) {
			throw ErrorCode.throwPaymentIdNotFound();
		}

		return new TicketDetailsDTO(ticketDetails);
	}

}
