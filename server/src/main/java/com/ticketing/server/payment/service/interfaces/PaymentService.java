package com.ticketing.server.payment.service.interfaces;

import com.ticketing.server.user.api.dto.response.SimplePaymentsResponse;

public interface PaymentService {

	SimplePaymentsResponse findSimplePayments(Long userId);

}
