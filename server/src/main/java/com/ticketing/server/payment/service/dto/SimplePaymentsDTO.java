package com.ticketing.server.payment.service.dto;

import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimplePaymentsDTO {

	private final Long userAlternateId;
	private final List<SimplePaymentDTO> payments;

	public SimplePaymentsResponse toResponse() {
		return new SimplePaymentsResponse(userAlternateId, payments);
	}

}
