package com.ticketing.server.global.exception.handle;

import com.ticketing.server.global.exception.PasswordMismatchException;
import com.ticketing.server.global.exception.handle.dto.ErrorResponseDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PasswordMismatchHandler {

	private static final String MESSAGE = "패스워드가 일치하지 않습니다";

	@ExceptionHandler(value = {PasswordMismatchException.class, BadCredentialsException.class})
	protected ResponseEntity<ErrorResponseDTO> handleException(RuntimeException e) {
		ErrorResponseDTO errorDTO = ErrorResponseDTO.of(e.getClass().getSimpleName(), List.of(MESSAGE));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
	}

}
