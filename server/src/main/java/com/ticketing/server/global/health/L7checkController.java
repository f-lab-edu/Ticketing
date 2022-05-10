package com.ticketing.server.global.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/l7check")
@RequiredArgsConstructor
public class L7checkController {

	private final MutableHealthIndicator indicator;

	@GetMapping
	public ResponseEntity<Object> health() {
		Health health = indicator.health();
		boolean isUp = health.getStatus().equals(Status.UP);
		return ResponseEntity
			.status(isUp ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE)
			.build();
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void down() {
		indicator.setHealth(Health.down().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void up() {
		indicator.setHealth(Health.up().build());
	}

}
