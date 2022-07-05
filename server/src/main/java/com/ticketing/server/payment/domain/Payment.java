package com.ticketing.server.payment.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.payment.service.dto.CreatePaymentDto;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends AbstractEntity {

	@NotNull
	private Long userId;

	@NotEmpty
	private String movieTitle;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private PaymentType type;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private PaymentStatus status;

	private String failedMessage;

	@NotEmpty
	private String paymentNumber;

	@NotNull
	private Integer totalPrice;

	private Payment(Long userId, String movieTitle, PaymentType type, PaymentStatus status, String paymentNumber, Integer totalPrice) {
		this.userId = userId;
		this.movieTitle = movieTitle;
		this.type = type;
		this.status = status;
		this.paymentNumber = paymentNumber;
		this.totalPrice = totalPrice;
	}

	public static Payment from(CreatePaymentDto dto) {
		return new Payment(dto.getUserId(),
			dto.getMovieTitle(),
			dto.getType(),
			dto.getStatus(),
			dto.getPaymentNumber(),
			dto.getTotalPrice());
	}

}
