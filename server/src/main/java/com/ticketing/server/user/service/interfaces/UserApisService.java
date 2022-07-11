package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.application.response.PaymentsResponse;
import javax.validation.constraints.NotNull;

public interface UserApisService {

	PaymentsResponse findPaymentsByEmail(@NotNull String email);

}
