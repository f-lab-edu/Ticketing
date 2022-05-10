package com.ticketing.server.global.health;

import java.util.concurrent.atomic.AtomicReference;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class ManualHealthIndicator implements MutableHealthIndicator {

	private final AtomicReference<Health> healthRef = new AtomicReference<>(Health.up().build());

	@Override
	public void setHealth(Health health) {
		healthRef.set(health);
	}

	@Override
	public Health health() {
		return healthRef.get();
	}

}
