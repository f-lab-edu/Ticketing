package com.ticketing.server.global.exception.handle;

import com.ticketing.server.global.exception.AlreadyDeletedException;
import com.ticketing.server.global.exception.handle.dto.ErrorResponseDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AlreadyDeletedHandler {

	@ExceptionHandler(value = AlreadyDeletedException.class)
	protected ResponseEntity<ErrorResponseDTO> handleException(AlreadyDeletedException e) {
		ErrorResponseDTO errorDTO = ErrorResponseDTO.of(e.getClass().getSimpleName(), List.of(e.getMessage()));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDTO);
	}

}
