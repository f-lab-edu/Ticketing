package com.ticketing.server.global.exception;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode
public class ErrorResponse {

	private final HttpStatus status;
	private final String message;
	private final List<String> errors;

	public ErrorResponse(HttpStatus status, String message, List<String> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ErrorResponse(HttpStatus status, String message, String error) {
		this.status = status;
		this.message = message;
		this.errors = List.of(error);
	}

	public static ErrorResponse toErrorResponse(ErrorCode errorCode) {
		return new ErrorResponse(
			errorCode.getHttpStatus(),
			errorCode.name(),
			errorCode.getDetail());
	}

}
