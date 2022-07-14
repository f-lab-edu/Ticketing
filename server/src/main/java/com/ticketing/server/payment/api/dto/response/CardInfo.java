package com.ticketing.server.payment.api.dto.response;

import com.ticketing.server.payment.api.dto.SnakeCaseStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardInfo extends SnakeCaseStrategy {

	private String purchaseCorp;

	private String purchaseCorpCode;

	private String issuerCorp;

	private String issuerCorpCode;

	private String kakaopayPurchaseCorp;

	private String kakaopayPurchaseCorpCode;

	private String kakaopayIssuerCorp;

	private String kakaopayIssuerCorpCode;

	private String bin;

	private String cardType;

	private String installMonth;

	private String approvedId;

	private String cardMid;

	private String interestFreeInstall;

	private String cardItemCode;

}
