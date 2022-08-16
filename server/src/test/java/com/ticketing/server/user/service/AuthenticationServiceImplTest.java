package com.ticketing.server.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ticketing.server.global.factory.YamlPropertySourceFactory;
import com.ticketing.server.global.redis.RefreshRedisRepository;
import com.ticketing.server.global.redis.RefreshToken;
import com.ticketing.server.global.security.jwt.JwtProperties;
import com.ticketing.server.global.security.jwt.JwtProvider;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.service.dto.TokenDTO;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = JwtProperties.class)
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
class AuthenticationServiceImplTest {

	@Autowired
	private JwtProperties useJwtProperties;
	private JwtProvider useJwtProvider;

	@Mock
	JwtProperties jwtProperties;

	@Mock
	JwtProvider jwtProvider;

	@Mock
	RefreshRedisRepository redisRepository;

	@InjectMocks
	AuthenticationServiceImpl authenticationService;

	UsernamePasswordAuthenticationToken authenticationToken;

	@BeforeEach
	void init() {
		useJwtProvider = new JwtProvider(useJwtProperties);
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(UserGrade.USER.name());
		authenticationToken =
			new UsernamePasswordAuthenticationToken("ticketing@gmail.com", "123456", Collections.singleton(grantedAuthority));
	}

	@Test
	@DisplayName("토큰 재발급 성공")
	void reissueAccessToken() {
		// given
		String refreshToken = "eyJhbGciOiJIUzUxMiJ9";
		when(jwtProvider.validateToken(any())).thenReturn(true);
		when(jwtProvider.getAuthentication(any())).thenReturn(authenticationToken);
		when(jwtProvider.generateTokenDto(any())).thenReturn(useJwtProvider.generateTokenDto(authenticationToken));
		when(redisRepository.findByEmail("ticketing@gmail.com")).thenReturn(Optional.of(new RefreshToken("ticketing@gmail.com", "eyJhbGciOiJIUzUxMiJ9")));
		when(jwtProperties.hasTokenStartsWith(refreshToken)).thenReturn(true);

		// when
		TokenDTO tokenDto = authenticationService.reissueTokenDto(refreshToken);

		// then
		assertAll(
			() -> assertThat(tokenDto.getAccessToken()).isNotEmpty()
			, () -> assertThat(tokenDto.getRefreshToken()).isNotEmpty()
			, () -> assertThat(tokenDto.getTokenType()).isEqualTo("Bearer")
			, () -> assertThat(tokenDto.getExpiresIn()).isEqualTo(60)
		);
	}

}
