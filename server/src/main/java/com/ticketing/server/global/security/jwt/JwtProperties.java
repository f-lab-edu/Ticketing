package com.ticketing.server.global.security.jwt;

import com.ticketing.server.global.factory.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(value = "jwt")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class JwtProperties {

	private final String accessHeader;
	private final String refreshHeader;
	private final String prefix;
	private final String secretKey;
	private final Integer accessTokenValidityInSeconds;
	private final Integer refreshTokenValidityInSeconds;

	public boolean hasTokenStartsWith(String token) {
		return token.startsWith(prefix);
	}
}
