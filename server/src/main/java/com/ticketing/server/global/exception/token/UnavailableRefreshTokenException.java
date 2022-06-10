package com.ticketing.server.global.exception.token;

public class UnavailableRefreshTokenException extends TokenException {

	private static final String MESSAGE = "사용할 수 없는 refresh Token 입니다.";

	public UnavailableRefreshTokenException() {
		super(MESSAGE);
	}

}
