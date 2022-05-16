package com.ticketing.server.user.exception;

public class DuplicateEmailException extends RuntimeException {

	private static final String MESSAGE = "이미 존재하는 이메일 입니다. :: %s";

	public DuplicateEmailException(String email) {
		super(String.format(MESSAGE, email));
	}

}
