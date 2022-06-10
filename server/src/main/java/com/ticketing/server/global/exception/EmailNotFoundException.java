package com.ticketing.server.global.exception;

public class EmailNotFoundException extends IllegalArgumentException {

	private static final String MESSAGE = "존재하지 않는 이메일 입니다.";

	public EmailNotFoundException() {
		super(MESSAGE);
	}

}
