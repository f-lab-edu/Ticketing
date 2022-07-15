package com.ticketing.server.payment.service.dto;

import com.ticketing.server.movie.service.dto.TicketsRefundResponse;
import com.ticketing.server.payment.api.dto.response.KakaoPayCancelResponse;
import com.ticketing.server.payment.application.response.PaymentRefundResponse;
import com.ticketing.server.payment.domain.Payment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRefundDTO {

	private Long paymentId;
	private List<Long> ticketIds;
	private String movieTitle;
	private Integer cancelAmount;
	private LocalDateTime canceledAt;

	public PaymentRefundDTO(Payment payment, KakaoPayCancelResponse kakaoPayCancelResponse, TicketsRefundResponse refundResponse) {
		this(
			payment.getId(),
			refundResponse.getTicketIds(),
			kakaoPayCancelResponse.getItemName(),
			kakaoPayCancelResponse.getTotalAmount(),
			kakaoPayCancelResponse.getCanceledAt()
		);
	}

	public PaymentRefundResponse toResponse() {
		return new PaymentRefundResponse(
			paymentId,
			ticketIds,
			movieTitle,
			cancelAmount,
			canceledAt
		);
	}
}
