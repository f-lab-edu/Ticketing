package com.ticketing.server.payment.api.dto.requset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class KakaoPayApproveRequest {

	// 가맹점 코드 현재는 테스트 값
	@NotEmpty
	private String cid = "TC0ONETIME";

	@feign.form.FormProperty("cid_secret")
	private String cidSecret;

	// 결제 고유 번호 ready res 에 포함
	@NotEmpty
	private String tid;

	// 주문번호 - 결제 예매번호?
	@NotEmpty
	@feign.form.FormProperty("partner_order_id")
	private String partnerOrderId;

	// 가맹점 회원 id - user 대체 ID
	@NotEmpty
	@feign.form.FormProperty("partner_user_id")
	private String partnerUserId;

	// 결제승인 요청 인증 토큰
	@NotEmpty
	@feign.form.FormProperty("pg_token")
	private String pgToken;

	private String payload;

	@feign.form.FormProperty("total_amount")
	private Integer totalAmount;

	public KakaoPayApproveRequest(String tid, String partnerOrderId, String partnerUserId, String pgToken) {
		this.tid = tid;
		this.partnerOrderId = partnerOrderId;
		this.partnerUserId = partnerUserId;
		this.pgToken = pgToken;
	}

}
