package com.ticketing.server.payment.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.global.redis.PaymentCache;
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

	@NotEmpty
	@Column(name = "tid", nullable = false)
	private String tid;

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

	public Payment(PaymentCache paymentCache, PaymentType type, PaymentStatus status, Integer totalAmount) {
		this(
			paymentCache.getUserAlternateId(),
			paymentCache.getMovieTitle(),
			paymentCache.getTid(),
			type,
			status,
			paymentCache.getPaymentNumber().toString(),
			totalAmount
		);
	}

	private Payment(Long userAlternateId, String movieTitle, String tid, PaymentType type, PaymentStatus status, String paymentNumber, Integer totalPrice) {
		this.userAlternateId = userAlternateId;
		this.movieTitle = movieTitle;
		this.tid = tid;
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

	public void refund() {
		status = PaymentStatus.REFUNDED;
	}
}
