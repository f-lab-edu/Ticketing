package com.ticketing.server.movie.service;

import static com.ticketing.server.movie.domain.TicketTest.setupTickets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.movie.domain.Ticket;
import com.ticketing.server.movie.domain.repository.TicketRepository;
import com.ticketing.server.movie.service.dto.TicketDetailsDTO;
import com.ticketing.server.movie.service.dto.TicketsReservationDTO;
import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

	@Mock
	TicketRepository ticketRepository;

	@InjectMocks
	TicketServiceImpl ticketService;

	@Test
	@DisplayName("paymentId 로 Ticket 목록 조회 실패 - paymentId 가 없는 경우")
	void findTicketsByPaymentIdFail() {
		// given
		when(ticketRepository.findTicketFetchJoinByPaymentId(1L)).thenReturn(Collections.emptyList());

		// when
		// then
		assertThatThrownBy(() -> ticketService.findTicketsByPaymentId(1L))
			.isInstanceOf(TicketingException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.PAYMENT_ID_NOT_FOUND);
	}

	@Test
	@DisplayName("paymentId 로 Ticket 목록 조회 성공")
	void findTicketsByPaymentIdSuccess() {
		// given
		List<Ticket> tickets = setupTickets();
		when(ticketRepository.findTicketFetchJoinByPaymentId(1L)).thenReturn(List.of(tickets.get(0)));

		// when
		TicketDetailsDTO ticketDetailDto = ticketService.findTicketsByPaymentId(1L);
		List<TicketDetailDTO> ticketDetails = ticketDetailDto.getTicketDetails();

		// then
		assertAll(
			() -> assertThat(ticketDetails).hasSize(1),
			() -> assertThat(ticketDetails.get(0).getPrice()).isEqualTo(15_000)
		);
	}

	@Test
	@DisplayName("티켓목록 예약으로 변경 시 조회된 갯수랑 다른 경우")
	void ticketReservationFail() {
	    // given
		List<Ticket> tickets = setupTickets();
		List<Ticket> list = List.of(tickets.get(0), tickets.get(1), tickets.get(2));
		List<Long> ids = List.of(0L, 1L, 2L, 10000L);

		when(ticketRepository.findTicketFetchJoinByTicketIds(ids)).thenReturn(list);

	    // when
		// then
		assertThatThrownBy(() -> ticketService.ticketReservation(ids))
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

		when(ticketRepository.findTicketFetchJoinByTicketIds(ids)).thenReturn(list);

	    // when
		TicketsReservationDTO ticketReservationsDto = ticketService.ticketReservation(ids);

		// then
		assertThat(ticketReservationsDto.getTicketReservationDtoList()).hasSize(3);
	}

}
