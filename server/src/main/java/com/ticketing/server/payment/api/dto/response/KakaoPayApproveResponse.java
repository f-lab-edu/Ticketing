package com.ticketing.server.payment.api.dto.response;


import com.ticketing.server.payment.api.dto.SnakeCaseStrategy;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoPayApproveResponse extends SnakeCaseStrategy {

	private String aid;

	private String tid;

	private String cid;

	private String sid;

	private String partnerOrderId;

	private String partnerUserId;

	private String paymentMethodType;

	private Amount amount;

	private CardInfo cardInfo;

	private String itemName;

	private String itemCode;

	private Integer quantity;

	private LocalDateTime createdAt;

	private LocalDateTime approvedAt;

	private String payload;

	public Integer getTotalAmount() {
		return amount.getTotal();
	}

}
