package com.ticketing.server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketingException extends RuntimeException {

	private final ErrorCode errorCode;

}
