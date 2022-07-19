package com.ticketing.server.global.config;

import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {

	@Override
	public SecurityContext createSecurityContext(WithAuthUser annotation) {
		String email = annotation.email();
		String role = annotation.role();
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

		User authUser = new User(email, "", authorities);
		UsernamePasswordAuthenticationToken token =
			new UsernamePasswordAuthenticationToken(authUser, "", authorities);
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(token);
		return context;
	}

}
