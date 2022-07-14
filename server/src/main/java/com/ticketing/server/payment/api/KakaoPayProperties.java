package com.ticketing.server.payment.api;

import com.ticketing.server.global.factory.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(value = "api.kakao.pay")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class KakaoPayProperties {

	private final String url;
	private final String approval;
	private final String cancel;
	private final String fail;
	private final String prefix;
	private final String key;

	public String getApprovalUrl() {
		return url + approval;
	}

	public String getCancelUrl() {
		return url + cancel;
	}

	public String getFailUrl() {
		return url + fail;
	}

	public String getAuthorization() {
		return prefix + " " + key;
	}

}
