package com.ticketing.server.global.exception.handle;

import com.ticketing.server.global.exception.EmailNotFoundException;
import com.ticketing.server.global.exception.handle.dto.ErrorResponseDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class EmailNotFoundHandler {

	@ExceptionHandler(value = EmailNotFoundException.class)
	protected ResponseEntity<ErrorResponseDTO> handleException(EmailNotFoundException e) {
		ErrorResponseDTO errorDTO = ErrorResponseDTO.of(e.getClass().getSimpleName(), List.of(e.getMessage()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
	}

}
