package com.ticketing.server.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* 400 BAD_REQUEST : 잘못된 요청 */
	MISMATCH_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	TOKEN_TYPE(BAD_REQUEST, "토큰 타입이 올바르지 않습니다."),
	UNAVAILABLE_REFRESH_TOKEN(BAD_REQUEST, "사용할 수 없는 토큰 입니다."),

	/* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
	EMAIL_NOT_FOUND(NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
	REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "리프레쉬 토큰을 찾을 수 없습니다."),

	/* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
	DUPLICATE_EMAIL(CONFLICT, "이메일이 이미 존재합니다."),
	DELETED_EMAIL(CONFLICT, "이미 삭제된 이메일 입니다.");

	private final HttpStatus httpStatus;
	private final String detail;

}
