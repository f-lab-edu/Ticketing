package com.ticketing.server.payment.api.dto.requset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class KakaoPayCancelRequest {

	@NotEmpty
	private String cid = "TC0ONETIME";

	@feign.form.FormProperty("cid_secret")
	private String cidSecret;

	@NotEmpty
	private String tid;

	@NotEmpty
	@feign.form.FormProperty("cancel_amount")
	private Integer cancelAmount;

	@NotEmpty
	@feign.form.FormProperty("cancel_tax_free_amount")
	private Integer cancelTaxFreeAmount;

	@feign.form.FormProperty("cancel_vat_amount")
	private Integer cancelVatAmount;

	@feign.form.FormProperty("cancel_available_amount")
	private Integer cancelAvailableAmount;

	private String payload;

	public KakaoPayCancelRequest(String tid, Integer cancelAmount) {
		this(tid, cancelAmount, 0);
	}

	public KakaoPayCancelRequest(String tid, Integer cancelAmount, int cancelTaxFreeAmount) {
		this.tid = tid;
		this.cancelAmount = cancelAmount;
		this.cancelTaxFreeAmount = cancelTaxFreeAmount;
	}

}
