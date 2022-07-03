package com.ticketing.server.global.security;

import com.ticketing.server.global.security.jwt.JwtFilter;
import com.ticketing.server.global.security.jwt.JwtSecurityConfig;
import com.ticketing.server.global.security.jwt.handle.JwtAccessDeniedHandler;
import com.ticketing.server.global.security.jwt.handle.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtFilter jwtFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			// 시큐리티는 기본적으로 세션을 사용하지만, jwt 을 위해 세션을 Stateless 로 설정
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/auth/token").permitAll()
			.antMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
			.antMatchers(HttpMethod.POST, "/api/users").permitAll()
			.antMatchers("/api/movies/**").permitAll()
			.antMatchers("/api/movieTimes/**").permitAll()
			.antMatchers("/l7check").permitAll()
			.antMatchers("/actuator/**").permitAll()
			.antMatchers("/api/v3/", "/swagger-ui/**", "/swagger/", "/swagger-resources/**", "/v3/api-docs").permitAll()
			.anyRequest().authenticated()
			.and()
			.apply(new JwtSecurityConfig(jwtFilter));
	}

}
