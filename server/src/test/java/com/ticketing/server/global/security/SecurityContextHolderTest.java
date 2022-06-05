package com.ticketing.server.global.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class SecurityContextHolderTest {

	private static final String ANY_USER = "ticketing";
	private static final String ANY_PASSWORD = "password";
	private static final String ROLE_GUEST = "ROLE_GUEST";

	@BeforeEach
	void init() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_THREADLOCAL);
		setMockAuthentication();
	}

	@AfterEach
	void clear() {
		SecurityContextHolder.clearContext();
	}

	@Test
	@DisplayName("SecurityContextHolder 에 현재 인증된 사용자 정보를 확인한다.")
	void SecurityContextHolder() {
		// given
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// when
		// then
		assertAll(
			() -> assertThat(authentication.isAuthenticated()).isTrue()
			, () -> assertThat(authentication.getName()).isEqualTo(ANY_USER)
			, () -> assertThat(authentication.getCredentials()).isEqualTo(ANY_PASSWORD)
			, () -> assertThat(authentication.getAuthorities())
				.extracting(GrantedAuthority::getAuthority)
				.contains(ROLE_GUEST)
		);

	}


	private void setMockAuthentication() {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		TestingAuthenticationToken mockAuthentication
			= new TestingAuthenticationToken(SecurityContextHolderTest.ANY_USER, SecurityContextHolderTest.ANY_PASSWORD, SecurityContextHolderTest.ROLE_GUEST);
		context.setAuthentication(mockAuthentication);

		SecurityContextHolder.setContext(context);
	}

}
