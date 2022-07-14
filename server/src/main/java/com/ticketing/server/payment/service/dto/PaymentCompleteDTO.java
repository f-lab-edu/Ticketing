package com.ticketing.server.payment.service.dto;

import com.ticketing.server.payment.api.dto.response.KakaoPayApproveResponse;
import com.ticketing.server.payment.application.response.PaymentCompleteResponse;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentCompleteDTO {

	private final String email;
	private final String tid;
	private final String movieTitle;
	private final Integer quantity;
	private final Integer totalAmount;

	public PaymentCompleteDTO(@NotEmpty String email, KakaoPayApproveResponse kakaoPayApproveResponse) {
		this(
			email,
			kakaoPayApproveResponse.getTid(),
			kakaoPayApproveResponse.getItemName(),
			kakaoPayApproveResponse.getQuantity(),
			kakaoPayApproveResponse.getTotalAmount()
		);
	}

	public PaymentCompleteResponse toResponse() {
		return new PaymentCompleteResponse(email, tid, movieTitle, quantity, totalAmount);
	}

}
