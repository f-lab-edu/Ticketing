package com.ticketing.server.payment.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import javax.persistence.Column;
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
	@Column(name = "user_alternate_id", nullable = false)
	private Long userAlternateId;

	@NotEmpty
	@Column(name = "movie_title", nullable = false)
	private String movieTitle;

	@NotNull
	@Column(name = "type", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PaymentType type;

	@NotNull
	@Column(name = "status", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PaymentStatus status;

	@Column(name = "failed_message")
	private String failedMessage;

	@NotEmpty
	@Column(name = "payment_number", nullable = false)
	private String paymentNumber;

	@NotNull
	@Column(name = "total_price", nullable = false)
	private Integer totalPrice;

	public Payment(Long userAlternateId, String movieTitle, PaymentType type, PaymentStatus status, String paymentNumber, Integer totalPrice) {
		this.userAlternateId = userAlternateId;
		this.movieTitle = movieTitle;
		this.type = type;
		this.status = status;
		this.paymentNumber = paymentNumber;
		this.totalPrice = totalPrice;
	}

	Payment(Long id, Long userAlternateId, String movieTitle, PaymentType type, PaymentStatus status, String paymentNumber, Integer totalPrice) {
		this.id = id;
		this.userAlternateId = userAlternateId;
		this.movieTitle = movieTitle;
		this.type = type;
		this.status = status;
		this.paymentNumber = paymentNumber;
		this.totalPrice = totalPrice;
	}

}
