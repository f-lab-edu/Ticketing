package com.ticketing.server.user.service.dto;

import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import com.ticketing.server.payment.service.dto.SimplePaymentDTO;
import com.ticketing.server.user.application.response.PaymentsResponse;
import com.ticketing.server.user.domain.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentsDTO {

	private final String email;
	private final List<SimplePaymentDTO> payments;

	public PaymentsDTO(User user, SimplePaymentsResponse simplePayments) {
		this(user.getEmail(), simplePayments.getPayments());
	}

	public PaymentsResponse toResponse() {
		return new PaymentsResponse(email, payments);
	}

}
