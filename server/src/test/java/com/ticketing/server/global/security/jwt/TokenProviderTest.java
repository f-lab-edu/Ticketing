package com.ticketing.server.global.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.ticketing.server.global.factory.YamlPropertySourceFactory;
import com.ticketing.server.user.application.response.TokenDto;
import com.ticketing.server.user.domain.UserGrade;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = JwtProperties.class)
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
class TokenProviderTest {

	@Autowired
	private JwtProperties jwtProperties;

	JwtProvider jwtProvider;


	@BeforeEach
	void init() {
		jwtProvider = new JwtProvider(jwtProperties);
	}

	@Test
	@DisplayName("토큰 생성 성공")
	void createTokenSuccess() {
		// given
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(UserGrade.USER.name());
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken("ticketing@gmail.com", "123456", Collections.singleton(grantedAuthority));

		// when
		TokenDto tokenDto = jwtProvider.generateTokenDto(authenticationToken);

		// then
		assertThat(tokenDto).isInstanceOf(TokenDto.class);
	}

	@Test
	@DisplayName("토큰 복호화 성공")
	void getAuthentication() {
		// given
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(UserGrade.USER.name());
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken("ticketing@gmail.com", "123456", Collections.singleton(grantedAuthority));

		// when
		TokenDto tokenDto = jwtProvider.generateTokenDto(authenticationToken);
		Authentication authentication = jwtProvider.getAuthentication(tokenDto.getAccessToken());

		// then
		assertThat(authentication.getName()).isEqualTo("ticketing@gmail.com");
	}

}
