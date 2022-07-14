package com.ticketing.server.global.redis;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("Payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentCache {

	@Id
	@GeneratedValue
	@Column(name = "payment_ready_id")
	private Long id;

	@Indexed
	private String email;

	private String movieTitle;

	private String tid;

	private List<Long> ticketIds;

	private Long userAlternateId;

	private Long paymentNumber;

	public PaymentCache(String email, String movieTitle, String tid, List<Long> ticketIds, Long userAlternateId, Long paymentNumber) {
		this.email = email;
		this.movieTitle = movieTitle;
		this.tid = tid;
		this.ticketIds = ticketIds;
		this.userAlternateId = userAlternateId;
		this.paymentNumber = paymentNumber;
	}
}
