package com.ticketing.server.payment.api.dto.requset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class KakaoPayReadyRequest {

	// 가맹점 코드
	@NotEmpty
	private String cid = "TC0ONETIME";

	// 가맹점 코드 인증키
	@feign.form.FormProperty("cid_secret")
	private String cidSecret;

	// 가맹점 주문번호 - 결제 예매번호?
	@NotEmpty
	@feign.form.FormProperty("partner_order_id")
	private String partnerOrderId;

	// 가맹점 회원 id - user 대체 ID
	@NotEmpty
	@feign.form.FormProperty("partner_user_id")
	private String partnerUserId;

	// 상품명 - 영화제목
	@NotEmpty
	@feign.form.FormProperty("item_name")
	private String itemName;

	// 상품코드
	@feign.form.FormProperty("item_code")
	private String itemCode;

	// 상품수량 - 티켓 수량
	@NotNull
	private Integer quantity;

	// 상품 총액 - 티켓 금액 합계
	@NotNull
	@feign.form.FormProperty("total_amount")
	private Integer totalAmount;

	// 상품 비과세 - 0원으로 일단 일괄처리
	@NotNull
	@feign.form.FormProperty("tax_free_amount")
	private Integer taxFreeAmount = 0;

	// 상품 부가세 금액
	@feign.form.FormProperty("vat_amount")
	private Integer vatAmount;

	// 컵 보증금
	@feign.form.FormProperty("green_deposit")
	private Integer greenDeposit;

	// 결제 성공 시 redirect URL - json 반환 용 URL ?
	@NotNull
	@feign.form.FormProperty("approval_url")
	private String approvalUrl;

	// 결제 취소 시 redirect URL
	@NotNull
	@feign.form.FormProperty("cancel_url")
	private String cancelUrl;

	// 결제 실패 시 redirect URL
	@NotNull
	@feign.form.FormProperty("fail_url")
	private String failUrl;

	public KakaoPayReadyRequest(String partnerOrderId, String partnerUserId, String itemName, Integer quantity,
		Integer totalAmount, String approvalUrl, String cancelUrl, String failUrl) {
		this.partnerOrderId = partnerOrderId;
		this.partnerUserId = partnerUserId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.approvalUrl = approvalUrl;
		this.cancelUrl = cancelUrl;
		this.failUrl = failUrl;
	}
}
