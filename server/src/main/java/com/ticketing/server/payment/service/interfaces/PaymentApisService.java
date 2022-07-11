package com.ticketing.server.payment.service.interfaces;

import com.ticketing.server.payment.application.response.PaymentDetailResponse;
import javax.validation.constraints.NotNull;

public interface PaymentApisService {

	PaymentDetailResponse findPaymentDetail(@NotNull Long paymentId);
}
