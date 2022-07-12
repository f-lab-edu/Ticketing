package com.ticketing.server.payment.application.response;

import com.ticketing.server.payment.service.dto.SimplePaymentDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimplePaymentsResponse {

	private final Long userAlternateId;
	private final List<SimplePaymentDTO> payments;

}
