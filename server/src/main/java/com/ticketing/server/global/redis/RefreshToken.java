package com.ticketing.server.global.redis;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("RefreshToken")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

	@Id
	@GeneratedValue
	@Column(name = "refresh_token_id")
	private Long id;

	@Indexed
	private String email;
	private String token;

	public RefreshToken(String email, String token) {
		this.email = email;
		this.token = token;
	}

	public void changeToken(String token) {
		this.token = token;
	}

}
