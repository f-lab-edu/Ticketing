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
public class Amount extends SnakeCaseStrategy {

	private Integer total;

	private Integer taxFree;

	private Integer vat;

	private Integer point;

	private Integer discount;

	private Integer greenDeposit;

}
