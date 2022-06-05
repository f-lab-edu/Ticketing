package com.ticketing.server.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

	private SecurityUtil() {
	}

	public static String getCurrentUserEmail() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new IllegalStateException("Security Context 에 인증 정보가 없습니다.");
		}

		return authentication.getName();
	}

}
