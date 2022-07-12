package com.ticketing.server.payment.service.interfaces;

import com.ticketing.server.payment.service.dto.PaymentDetailDTO;
import javax.validation.constraints.NotNull;

public interface PaymentApisService {

	PaymentDetailDTO findPaymentDetail(@NotNull Long paymentId);
}
