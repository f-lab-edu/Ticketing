package com.ticketing.server.user.api.dto.request;

import com.ticketing.server.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimplePaymentsRequest {

	private Long userId;

	public static SimplePaymentsRequest from(User user) {
		return new SimplePaymentsRequest(user.getId());
	}

}
