package com.ticketing.server.global.exception.handle;

import com.ticketing.server.global.exception.handle.dto.ErrorResponseDTO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order
public class RuntimeHandler {

	@ExceptionHandler(value = RuntimeException.class)
	protected ResponseEntity<ErrorResponseDTO> handleException(RuntimeException e) {
		ErrorResponseDTO errorDTO = ErrorResponseDTO.of(e.getClass().getSimpleName(), List.of(e.getMessage()));
		return ResponseEntity.badRequest().body(errorDTO);
	}

}
