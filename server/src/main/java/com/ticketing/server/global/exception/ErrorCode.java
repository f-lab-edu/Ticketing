package com.ticketing.server.global.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.ticketing.server.global.redis.PaymentCache;
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
	UNABLE_CHANGE_GRADE(BAD_REQUEST, "동일한 등급으로 변경할 수 없습니다."),
	INVALID_TICKET_ID(BAD_REQUEST, "잘못된 티켓 ID가 존재합니다."),
	BAD_REQUEST_MOVIE_TIME(BAD_REQUEST, "동일한 상영시간만 결제 가능합니다."),
	BAD_REQUEST_PAYMENT_COMPLETE(BAD_REQUEST, "처리할 결제 정보가 존재하지 않습니다."),
	BAD_REQUEST_PAYMENT_READY(BAD_REQUEST, "이미 진행 중인 결제가 존재합니다."),
	BAD_REQUEST_PAYMENT_CANCEL(BAD_REQUEST, "취소할 티켓이 존재하지 않습니다."),

	/* 403 FORBIDDEN : 접근 권한 제한 */
	VALID_USER_ID(FORBIDDEN, "해당 정보에 접근 권한이 존재하지 않습니다."),

	/* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
	USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
	EMAIL_NOT_FOUND(NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
	MOVIE_NOT_FOUND(NOT_FOUND, "해당 제목의 영화를 찾을 수 없습니다."),
	MOVIE_TIME_NOT_FOUND(NOT_FOUND, "해당 영화 시간표 정보를 찾을 수 없습니다"),
	REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "리프레쉬 토큰을 찾을 수 없습니다."),
	PAYMENT_ID_NOT_FOUND(NOT_FOUND, "결제정보를 찾을 수 없습니다."),
	THEATER_NOT_FOUND(NOT_FOUND, "상영관 정보를 찾을 수 없습니다."),

	/* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
	DUPLICATE_EMAIL(CONFLICT, "이메일이 이미 존재합니다."),
	DUPLICATE_PAYMENT(CONFLICT, "해당 좌석은 현재 판매된 좌석입니다."),
	DUPLICATE_MOVIE(CONFLICT, "해당 영화 정보가 이미 존재합니다."),
	DUPLICATE_MOVIE_TIME(CONFLICT, "해당 영화 시간표 정보가 이미 존재합니다."),
	DELETED_EMAIL(CONFLICT, "이미 삭제된 이메일 입니다."),
	DELETED_MOVIE(CONFLICT, "이미 삭제된 영화 입니다.");

	private final HttpStatus httpStatus;
	private String detail;

	/* 400 BAD_REQUEST : 잘못된 요청 */
	public static TicketingException throwMismatchPassword() {
		throw new TicketingException(MISMATCH_PASSWORD);
	}

	public static TicketingException throwTokenType() {
		throw new TicketingException(TOKEN_TYPE);
	}

	public static TicketingException throwUnavailableRefreshToken() {
		throw new TicketingException(UNAVAILABLE_REFRESH_TOKEN);
	}

	public static TicketingException throwUnableChangeGrade() {
		throw new TicketingException(UNABLE_CHANGE_GRADE);
	}

	public static TicketingException throwInvalidTicketId() {
		throw new TicketingException(INVALID_TICKET_ID);
	}

	public static TicketingException throwBadRequestMovieTime() {
		throw new TicketingException(BAD_REQUEST_MOVIE_TIME);
	}

	public static TicketingException throwBadRequestPaymentComplete() {
		throw new TicketingException(BAD_REQUEST_PAYMENT_COMPLETE);
	}

	public static TicketingException throwBadRequestPaymentReady() {
		throw new TicketingException(BAD_REQUEST_PAYMENT_READY);
	}

	public static TicketingException throwBadRequestPaymentCancel() {
		throw new TicketingException(BAD_REQUEST_PAYMENT_CANCEL);
	}

	/* 403 FORBIDDEN : 접근 권한 제한 */
	public static TicketingException throwValidUserId() {
		throw new TicketingException(VALID_USER_ID);
	}

	/* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
	public static TicketingException throwUserNotFound() {
		throw new TicketingException(USER_NOT_FOUND);
	}

	public static TicketingException throwEmailNotFound() {
		throw new TicketingException(EMAIL_NOT_FOUND);
	}

	public static TicketingException throwMovieNotFound() {
		throw new TicketingException(MOVIE_NOT_FOUND);
	}

	public static TicketingException throwMovieTimeNotFound() {
		throw new TicketingException(MOVIE_TIME_NOT_FOUND);
	}

	public static TicketingException throwRefreshTokenNotFound() {
		throw new TicketingException(REFRESH_TOKEN_NOT_FOUND);
	}

	public static TicketingException throwPaymentIdNotFound() {
		throw new TicketingException(PAYMENT_ID_NOT_FOUND);
	}

	public static TicketingException throwTheaterNotFound() {
		throw new TicketingException(THEATER_NOT_FOUND);
	}

	/* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
	public static TicketingException throwDuplicateEmail() {
		throw new TicketingException(DUPLICATE_EMAIL);
	}

	public static TicketingException throwDuplicatePayment() {
		throw new TicketingException(DUPLICATE_PAYMENT);
	}

	public static TicketingException throwDuplicateMovie() {
		throw new TicketingException(DUPLICATE_MOVIE);
	}

	public static TicketingException throwDuplicateMovieTime() {
		throw new TicketingException(DUPLICATE_MOVIE_TIME);
	}

	public static TicketingException throwDeletedEmail() {
		throw new TicketingException(DELETED_EMAIL);
	}

	public static TicketingException throwDeletedMovie() {
		throw new TicketingException(DELETED_MOVIE);
	}


}
