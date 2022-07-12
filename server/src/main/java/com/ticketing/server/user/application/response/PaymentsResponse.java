package com.ticketing.server.user.application.response;

import com.ticketing.server.payment.service.dto.SimplePaymentDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentsResponse {

	private final String email;
	private final List<SimplePaymentDTO> payments;

}
