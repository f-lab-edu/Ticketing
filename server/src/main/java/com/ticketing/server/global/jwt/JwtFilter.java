package com.ticketing.server.global.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Configuration
public class JwtFilter extends OncePerRequestFilter {

	private final JwtProvider tokenProvider;
	private final String accessHeader;
	private final String tokenPrefix;

	public JwtFilter(JwtProperties jwtProperties, JwtProvider tokenProvider) {
		this.accessHeader = jwtProperties.getAccessHeader();
		this.tokenPrefix = jwtProperties.getPrefix();
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String jwt = resolveToken(request);

		// 토큰이 정상이면 Authentication 을 가져와서 SecurityContext 에 저장
		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(accessHeader);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix)) {
			return bearerToken.substring(7);
		}

		return null;
	}

}
