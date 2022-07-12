package com.ticketing.server.global.security.jwt;

import com.ticketing.server.user.service.dto.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

	private final Key key;
	private final String prefix;
	private final long accessTokenValidityInMilliseconds;
	private final long refreshTokenValidityInMilliseconds;

	public JwtProvider(JwtProperties jwtProperties) {
		byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
		this.key = Keys.hmacShaKeyFor(keyBytes);

		this.prefix = jwtProperties.getPrefix();
		this.accessTokenValidityInMilliseconds = jwtProperties.getAccessTokenValidityInSeconds() * 1000L;
		this.refreshTokenValidityInMilliseconds = jwtProperties.getRefreshTokenValidityInSeconds() * 1000L;
	}

	public TokenDTO generateTokenDto(Authentication authentication) {
		String accessToken = createAccessToken(authentication);
		String refreshToken = createRefreshToken(authentication);
		long expiresIn = accessTokenValidityInMilliseconds / 1000L;

		return new TokenDTO(accessToken, refreshToken, prefix, expiresIn);
	}

	private String createAccessToken(Authentication authentication) {
		// 만료시간 계산
		long now = (new Date()).getTime();
		Date accessTokenExpiresIn = new Date(now + this.accessTokenValidityInMilliseconds);

		return createToken(authentication, accessTokenExpiresIn);
	}

	private String createRefreshToken(Authentication authentication) {
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

		// 권한조회
		List<SimpleGrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(AUTHORITIES_DELIMITER))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String token) {
		parseClaims(token);
		return true;
	}

	private Claims parseClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

}

