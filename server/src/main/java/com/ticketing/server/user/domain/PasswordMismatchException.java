package com.ticketing.server.user.domain;

public class PasswordMismatchException extends RuntimeException {

	private static final String MESSAGE = "패스워드가 일치하지 않습니다";

	public PasswordMismatchException() {
		super(MESSAGE);
	}

}
