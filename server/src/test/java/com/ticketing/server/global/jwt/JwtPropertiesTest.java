package com.ticketing.server.global.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ticketing.server.global.factory.YamlPropertySourceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = JwtProperties.class)
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
class JwtPropertiesTest {

	@Autowired
	private JwtProperties jwtProperties;

	@Test
	@DisplayName("yml - jwt 설정파일 체크")
	void jwtPropertiesCheck() {
		// given
		// when
		// then
		assertAll(
			() -> assertThat(jwtProperties.getHeader()).isEqualTo("Authorization")
			, () -> assertThat(jwtProperties.getPrefix()).isEqualTo("Bearer")
			, () -> assertThat(jwtProperties.getTokenValidityInSeconds()).isEqualTo(86400)
			, () -> assertThat(jwtProperties.getSecretKey()).isNotEmpty());
	}

}