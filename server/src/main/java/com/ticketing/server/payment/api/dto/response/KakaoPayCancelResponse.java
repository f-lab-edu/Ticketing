package com.ticketing.server.payment.api.dto.response;

import com.ticketing.server.payment.api.dto.SnakeCaseStrategy;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class KakaoPayCancelResponse extends SnakeCaseStrategy {

	private String aid;

	private String tid;

	private String cid;

	private String status;

	private String partnerOrderId;

	private String partnerUserId;

	private String paymentMethodType;

	private Amount amount;

	private ApprovedCancelAmount approvedCancelAmount;

	private CanceledAmount canceledAmount;

	private CancelAvailableAmount cancelAvailableAmount;

	private String itemName;

	private String itemCode;

	private Integer quantity;

	private LocalDateTime createdAt;

	private LocalDateTime approvedAt;

	private LocalDateTime canceledAt;

	private String payload;

	public Integer getTotalAmount() {
		return amount.getTotal();
	}

}
