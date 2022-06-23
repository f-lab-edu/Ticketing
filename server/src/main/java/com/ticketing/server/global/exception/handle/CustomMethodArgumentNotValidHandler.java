package com.ticketing.server.global.exception.handle;

import com.ticketing.server.global.exception.handle.dto.ErrorResponseDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomMethodArgumentNotValidHandler {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentNotValidException e) {
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		List<String> messages = getMessages(allErrors.iterator());

		ErrorResponseDTO result = ErrorResponseDTO.of(e.getClass().getSimpleName(), messages);
		return ResponseEntity.badRequest().body(result);
	}

	private List<String> getMessages(Iterator<ObjectError> errorIterator) {
		List<String> messages = new ArrayList<>();

		while (errorIterator.hasNext()) {
			StringBuilder messageBuilder = new StringBuilder();
			ObjectError error = errorIterator.next();
			messageBuilder
				.append("['")
				.append(((FieldError) error).getField()) // 유효성 검사가 실패한 속성
				.append("' is '")
				.append(((FieldError) error).getRejectedValue()) // 유효하지 않은 값
				.append("' :: ")
				.append(error.getDefaultMessage()) // 유효성 검사 실패 시 메시지
				.append("]");

			log.error(messageBuilder.toString());
			messages.add(messageBuilder.toString());
		}

		return messages;
	}

}
