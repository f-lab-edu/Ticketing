package com.ticketing.server.global.exception.token;

public class TokenNotFindException extends TokenException {

	private static final String MESSAGE = "일치하는 토큰을 찾지 못하였습니다.";

	public TokenNotFindException() {
		super(MESSAGE);
	}
}
