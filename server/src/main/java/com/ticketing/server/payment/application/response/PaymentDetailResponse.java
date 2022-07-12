package com.ticketing.server.payment.application.response;

import com.ticketing.server.payment.service.dto.TicketDetailDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDetailResponse {

	private Long paymentId;
	private String movieTitle;
	private String paymentNumber;
	private Integer totalPrice;
	private LocalDateTime createdAt;
	private List<TicketDetailDTO> tickets;

}
