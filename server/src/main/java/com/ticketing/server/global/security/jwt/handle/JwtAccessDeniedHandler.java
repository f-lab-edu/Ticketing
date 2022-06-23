package com.ticketing.server.global.security.jwt.handle;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("JwtAccessDeniedHandler")
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final HandlerExceptionResolver resolver;

	public JwtAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		resolver.resolveException(request, response, null, accessDeniedException);
	}
}
