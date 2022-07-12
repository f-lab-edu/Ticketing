package com.ticketing.server.user.application.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDeleteResponse {

	private final String name;
	private final String email;
	private final LocalDateTime deletedAt;

}
