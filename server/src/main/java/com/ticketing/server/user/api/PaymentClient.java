package com.ticketing.server.user.api;

import com.ticketing.server.payment.application.response.SimplePaymentsResponse;
import javax.validation.constraints.NotNull;

public interface PaymentClient {

	SimplePaymentsResponse getPayments(@NotNull Long alternateId);

}
