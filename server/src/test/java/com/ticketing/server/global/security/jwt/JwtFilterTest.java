package com.ticketing.server.global.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ticketing.server.global.factory.YamlPropertySourceFactory;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.UserGrade.ROLES;
import com.ticketing.server.user.service.dto.TokenDTO;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = JwtProperties.class)
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
class JwtFilterTest {

	@Autowired
	private JwtProperties jwtProperties;

	private MockHttpServletRequest mockRequest;
	private MockHttpServletResponse mockResponse;
	private MockFilterChain mockFilterChain;

	private JwtFilter jwtFilter;

	@BeforeEach
	void init() {
		mockRequest = new MockHttpServletRequest();
		mockResponse = new MockHttpServletResponse();
		mockFilterChain = new MockFilterChain();

		JwtProvider jwtProvider = new JwtProvider(jwtProperties);
		jwtFilter = new JwtFilter(jwtProperties, jwtProvider);

		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(UserGrade.USER.name());
		Collection<SimpleGrantedAuthority> authorities = Collections.singleton(grantedAuthority);
		User user = new User(
			"kdhyo98@gmail.com",
			"",
			authorities
		);
		TokenDTO tokenDto = jwtProvider.generateTokenDto(new UsernamePasswordAuthenticationToken(user, null, authorities));
		mockRequest.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

		SecurityContextHolder.clearContext();
	}

	@ParameterizedTest
	@DisplayName("Header 정보가 올바르지 않을 경우")
	@ValueSource(strings = {"Bearer tokenTest", "Bearer", "BearertokenTest"})
	void validateToken(String authorization) {
		// given
		mockRequest.removeHeader("Authorization");
		mockRequest.addHeader("Authorization", authorization);

		// when
		// then
		assertThatThrownBy(() -> jwtFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain))
			.isInstanceOf(RuntimeException.class);
	}

	@Test
	@DisplayName("다음 필터 실행")
	void continuesToNextFilter() throws ServletException, IOException {
		// given
		MockFilterChain mockFilterChainSpy = spy(this.mockFilterChain);

		// when
		jwtFilter.doFilter(mockRequest, mockResponse, mockFilterChainSpy);

		// then
		verify(mockFilterChainSpy, times(1)).doFilter(mockRequest, mockResponse);
	}

	@Test
	@DisplayName("setAuthentication 데이터 확인")
	void setsAuthenticationInSecurityContext() throws ServletException, IOException {
		// given
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLES.USER);
		Collection<GrantedAuthority> authorities = Collections.singleton(grantedAuthority);

		// when
		jwtFilter.doFilter(mockRequest, mockResponse, mockFilterChain);

		// then
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User principal = (User) authentication.getPrincipal();
		assertAll(
			() -> assertThat(principal.getUsername()).isEqualTo("kdhyo98@gmail.com"),
			() -> assertThat(principal.getAuthorities()).isEqualTo(authorities)
		);
	}

}
