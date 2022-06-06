package com.ticketing.server.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtProvider {

	private static final String AUTHORITIES_KEY = "auth";
	private static final String AUTHORITIES_DELIMITER = ",";

	private final long accessTokenValidityInMilliseconds;
	private final long refreshTokenValidityInMilliseconds;
	private final Key key;

	public JwtProvider(JwtProperties jwtProperties) {
		this.accessTokenValidityInMilliseconds = jwtProperties.getAccessTokenValidityInSeconds() * 1000L;
		this.refreshTokenValidityInMilliseconds = jwtProperties.getRefreshTokenValidityInSeconds() * 1000L;

		byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(Authentication authentication) {
		// 만료시간 계산
		long now = (new Date()).getTime();
		Date accessTokenExpiresIn = new Date(now + this.accessTokenValidityInMilliseconds);

		return createToken(authentication, accessTokenExpiresIn);
	}

	public String createRefreshToken(Authentication authentication) {
		// 만료시간 계산
		long now = (new Date()).getTime();
		Date refreshTokenExpiresIn = new Date(now + this.refreshTokenValidityInMilliseconds);

		return createToken(authentication, refreshTokenExpiresIn);
	}

	private String createToken(Authentication authentication, Date expiration) {
		// 권한 정보 가져오기
		String authorities = generateStringToAuthorities(authentication);

		// JWT 생성
		return Jwts.builder()
			.setSubject(authentication.getName()) // email
			.claim(AUTHORITIES_KEY, authorities) // payload
			.setExpiration(expiration) // 만료일
			.signWith(key, SignatureAlgorithm.HS512) // 서명 키 값
			.compact();
	}

	private String generateStringToAuthorities(Authentication authentication) {
		StringJoiner authorities = new StringJoiner(AUTHORITIES_DELIMITER);
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			String roleName = makeRoleName(grantedAuthority.getAuthority());
			authorities.add(roleName);
		}
		return authorities.toString();
	}

	private String makeRoleName(String role) {
		return "ROLE_" + role.toUpperCase();
	}

	public Authentication getAuthentication(String token) {
		// 토큰 복호화
		Claims claims = parseClaims(token);

		List<SimpleGrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(AUTHORITIES_DELIMITER))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (SecurityException | MalformedJwtException exception) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("잘못된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		}

		return false;
	}

	private Claims parseClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

}

