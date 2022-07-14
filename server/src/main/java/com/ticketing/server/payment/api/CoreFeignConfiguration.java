package com.ticketing.server.payment.api;

import feign.Logger;
import feign.Logger.Level;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;

public class CoreFeignConfiguration {

	@Bean
	Logger.Level feignLoggerLevel() {
		return Level.FULL;
	}

	@Bean
	Encoder formEncoder() {
		return new feign.form.FormEncoder();
	}

}
