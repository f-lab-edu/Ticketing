package com.ticketing.server.payment.api.dto.response;

import com.ticketing.server.payment.api.dto.SnakeCaseStrategy;
import lombok.Getter;

@Getter
public class CanceledAmount extends SnakeCaseStrategy {

	private Integer total;
	private Integer taxFree;
	private Integer vat;
	private Integer point;
	private Integer discount;
	private Integer greenDeposit;

}
