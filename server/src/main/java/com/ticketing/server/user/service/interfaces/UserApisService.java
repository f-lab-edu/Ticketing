package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.service.dto.PaymentsDTO;
import javax.validation.constraints.NotNull;

public interface UserApisService {

	PaymentsDTO findPaymentsByEmail(@NotNull String email);

}
