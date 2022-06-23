package com.ticketing.server.global.exception.handle.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseDTO {

	private String code;
	private List<String> messages;

	private ErrorResponseDTO(String code, List<String> messages) {
		this.code = code;
		this.messages = messages;
	}

	public static ErrorResponseDTO of(String code, List<String> messages) {
		return new ErrorResponseDTO(code, messages);
	}

}
