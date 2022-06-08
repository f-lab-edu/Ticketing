package com.ticketing.server.global.exception.token;

public class TokenTypeException extends TokenException {

	private static final String MESSAGE = "토큰 타입이 일치하지 않습니다.";

	public TokenTypeException() {
		super(MESSAGE);
	}
}
