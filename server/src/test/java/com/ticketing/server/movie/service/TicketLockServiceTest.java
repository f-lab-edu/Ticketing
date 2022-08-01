package com.ticketing.server.movie.service;

import static com.ticketing.server.movie.domain.TicketTest.setupTickets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.movie.service.dto.TicketIdsDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketLockServiceTest {

	@Mock
	TicketRepository ticketRepository;

	@InjectMocks
	TicketLockService ticketLockService;

	@Test
	@DisplayName("티켓목록 예약으로 변경 시 조회된 갯수랑 다른 경우")
	void ticketReservationFail() {
		// given
		List<Ticket> tickets = setupTickets();
		List<Ticket> list = List.of(tickets.get(0), tickets.get(1), tickets.get(2));
		List<Long> ids = List.of(0L, 1L, 2L, 10000L);
		TicketIdsDTO ticketIdsDto = new TicketIdsDTO(ids);

		when(ticketRepository.findTicketFetchJoinByTicketIds(ids)).thenReturn(list);

		// when
		// then
		assertThatThrownBy(() -> ticketLockService.ticketReservation(ticketIdsDto))
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.INVALID_TICKET_ID);
	}

	@Test
	@DisplayName("티켓목록 예약으로 변경 완료")
	void ticketReservationSuccess() {
		// given
		List<Ticket> tickets = setupTickets();
		List<Ticket> list = List.of(tickets.get(0), tickets.get(1), tickets.get(2));
		List<Long> ids = List.of(0L, 1L, 2L);
		TicketIdsDTO ticketIdsDto = new TicketIdsDTO(ids);

		when(ticketRepository.findTicketFetchJoinByTicketIds(ids)).thenReturn(list);

		// when
		TicketsReservationDTO ticketReservationsDto = ticketLockService.ticketReservation(ticketIdsDto);

		// then
		assertThat(ticketReservationsDto.getTicketReservationDtoList()).hasSize(3);
	}

}
