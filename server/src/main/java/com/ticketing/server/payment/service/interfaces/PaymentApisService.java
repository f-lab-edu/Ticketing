package com.ticketing.server.payment.service.interfaces;

import com.ticketing.server.global.validator.constraints.NotEmptyCollection;
import com.ticketing.server.payment.service.dto.PaymentCancelDTO;
import com.ticketing.server.payment.service.dto.PaymentCompleteDTO;
import com.ticketing.server.payment.service.dto.PaymentDetailDTO;
import com.ticketing.server.payment.service.dto.PaymentReadyDTO;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface PaymentApisService {

	PaymentDetailDTO findPaymentDetail(@NotNull Long paymentId);

	PaymentReadyDTO ready(@NotEmptyCollection List<Long> ticketIds);

	PaymentCompleteDTO complete(@NotEmpty String email, @NotEmpty String pgToken);

	PaymentCancelDTO cancel(@NotEmpty String email);

}
