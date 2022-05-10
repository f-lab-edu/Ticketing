package com.ticketing.server.payment.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.user.domain.User;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Payment extends AbstractEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
	private User user;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private PaymentType type;

	@NotNull
	@Enumerated(value = EnumType.STRING)
	private PaymentStatus status;

	private String failedMessage;

	@NotNull
	private String paymentNumber;

	@NotNull
	private Integer totalPrice;

}
