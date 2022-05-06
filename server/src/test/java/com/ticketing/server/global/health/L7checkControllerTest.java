package com.ticketing.server.global.health;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class L7checkControllerTest {

	private static final String L7CHECK = "/l7check";
	private static final String HEALTH = "/actuator/health";

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void downAndUp() throws InterruptedException {
		// before
		expectUrlStatus(L7CHECK, HttpStatus.OK);
		expectUrlStatus(HEALTH, HttpStatus.OK);

		// down
		restTemplate.delete(L7CHECK);

		// then down
		TimeUnit.MILLISECONDS.sleep(1000);
		expectUrlStatus(L7CHECK, HttpStatus.SERVICE_UNAVAILABLE);
		expectUrlStatus(HEALTH, HttpStatus.SERVICE_UNAVAILABLE);

		// up
		restTemplate.postForEntity(L7CHECK, null, Object.class);

		// then up
		TimeUnit.MILLISECONDS.sleep(1000);
		expectUrlStatus(L7CHECK, HttpStatus.OK);
		expectUrlStatus(HEALTH, HttpStatus.OK);
	}

	private void expectUrlStatus(String url, HttpStatus status) {
		ResponseEntity<Object> res = restTemplate.getForEntity(url, Object.class);
		assertThat(res.getStatusCode()).isEqualTo(status);
	}

}