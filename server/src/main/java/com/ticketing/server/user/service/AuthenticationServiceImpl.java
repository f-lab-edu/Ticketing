package com.ticketing.server.user.service;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.redis.RefreshRedisRepository;
import com.ticketing.server.global.redis.RefreshToken;
import com.ticketing.server.global.security.jwt.JwtProperties;
import com.ticketing.server.global.security.jwt.JwtProvider;
import com.ticketing.server.user.service.dto.DeleteRefreshTokenDTO;
import com.ticketing.server.user.service.dto.TokenDTO;
import com.ticketing.server.user.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final RefreshRedisRepository refreshRedisRepository;

	private final JwtProvider jwtProvider;
	private final JwtProperties jwtProperties;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Override
	@Transactional
	public TokenDTO generateTokenDto(UsernamePasswordAuthenticationToken authenticationToken) {
		// 회원인증
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		String email = authenticationToken.getName();

		// 토큰 발급
		TokenDTO tokenDto = jwtProvider.generateTokenDto(authentication);

		// refresh 토큰이 있으면 수정, 없으면 생성
		refreshRedisRepository.findByEmail(email)
			.ifPresentOrElse(
				tokenEntity -> refreshRedisRepository.save(
					tokenEntity.changeToken(
						tokenDto.getRefreshToken()
					)
				),
				() -> refreshRedisRepository.save(
					new RefreshToken(email, tokenDto.getRefreshToken())
				)
			);

		return tokenDto;
	}

	@Override
	@Transactional
	public TokenDTO reissueTokenDto(String bearerRefreshToken) {
		String refreshToken = resolveToken(bearerRefreshToken);

		// 토큰 검증
		jwtProvider.validateToken(refreshToken);

		Authentication authentication = jwtProvider.getAuthentication(refreshToken);

		// Redis 에 토큰이 있는지 검증
		RefreshToken findTokenEntity = refreshRedisRepository.findByEmail(authentication.getName())
			.orElseThrow(ErrorCode::throwRefreshTokenNotFound);

		// redis 토큰과 input 토큰이 일치한지 확인
		if (!refreshToken.equals(findTokenEntity.getToken())) {
			throw ErrorCode.throwUnavailableRefreshToken();
		}

		// 토큰 발급
		TokenDTO tokenDto = jwtProvider.generateTokenDto(authentication);

		// 토큰 최신화
		findTokenEntity.changeToken(tokenDto.getRefreshToken());
		refreshRedisRepository.save(findTokenEntity);

		return tokenDto;
	}

	@Override
	@Transactional
	public DeleteRefreshTokenDTO deleteRefreshToken(String email) {
		return refreshRedisRepository.findByEmail(email)
			.map(tokenDto -> {
				refreshRedisRepository.delete(tokenDto);
				return new DeleteRefreshTokenDTO(tokenDto);
			}).orElseGet(
				() -> new DeleteRefreshTokenDTO(email)
			);
	}

	private String resolveToken(String bearerToken) {
		if (StringUtils.hasText(bearerToken) && jwtProperties.hasTokenStartsWith(bearerToken)) {
			return bearerToken.substring(7);
		}
		throw ErrorCode.throwTokenType();
	}

}
