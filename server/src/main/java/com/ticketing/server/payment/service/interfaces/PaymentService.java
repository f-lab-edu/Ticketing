package com.ticketing.server.payment.service.interfaces;

import com.ticketing.server.payment.service.dto.SimplePaymentsDTO;
import javax.validation.constraints.NotNull;

public interface PaymentService {

	SimplePaymentsDTO findSimplePayments(@NotNull Long userAlternateId);

}
