package com.ticketing.server.global.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public interface MutableHealthIndicator extends HealthIndicator {

	void setHealth(Health health);

}
