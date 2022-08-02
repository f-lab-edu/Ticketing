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
		List<TicketDetailDTO> ticketDetails = ticketService.findTicketsByPaymentId(1L);

		// then
		assertAll(
			() -> assertThat(ticketDetails).hasSize(1),
			() -> assertThat(ticketDetails.get(0).getPrice()).isEqualTo(15_000)
		);
	}

}
