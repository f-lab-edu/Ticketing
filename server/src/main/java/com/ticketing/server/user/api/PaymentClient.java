package com.ticketing.server.user.api;

import com.ticketing.server.user.api.dto.request.SimplePaymentsRequest;
import com.ticketing.server.user.api.dto.response.SimplePaymentsResponse;

public interface PaymentClient {

	SimplePaymentsResponse getSimplePayments(SimplePaymentsRequest request);

}
