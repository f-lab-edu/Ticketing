package com.ticketing.server.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SnowflakeTest {

	@Test
	@DisplayName("비트 계산이 정상적으로 처리되었는지 확인")
	void nextId_shouldGenerateIdWithCorrectBitsFilled() {
		// given
		int nodeId = 784;
		int sequence = 0;
		Snowflake snowflake = new Snowflake();
		long beforeTimestamp = Instant.now().toEpochMilli();

		// when
		long id = snowflake.generateId();

		// then
		long[] attrs = snowflake.parse(id);
		assertAll(
			() -> assertThat(attrs[0] >= beforeTimestamp).isTrue(),
			() -> assertThat(sequence).isEqualTo(attrs[2])
		);
	}

	@Test
	@DisplayName("50_000 번 돌렸을 시 유니크 ID 인지 확인")
	void nextId_shouldGenerateUniqueId() {
		// given
		Set<Long> ids = new HashSet<>();
		Snowflake snowflake = new Snowflake();
		int iterations = 50_000;

		// when
		for (int i = 0; i < iterations; i++) {
			ids.add(snowflake.generateId());
		}

		// then
		assertThat(ids).hasSize(iterations);
	}

	@Test
	@DisplayName("멀티스레드 환경 유니크 ID 테스트")
	void nextId_shouldGenerateUniqueIdIfCalledFromMultipleThreads() throws InterruptedException, ExecutionException {
		// given
		int numThreads = 50;
		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
		CountDownLatch latch = new CountDownLatch(numThreads);

		Snowflake snowflake = new Snowflake();
		int iterations = 10_000;

		// when
		Future<Long>[] futures = new Future[iterations];
		for (int i = 0; i < iterations; i++) {
			futures[i] = executorService.submit(() -> {
				long id = snowflake.generateId();
				latch.countDown();
				return id;
			});
		}

		// then
		latch.await();
		
		Set<Future<Long>> set = Arrays.stream(futures)
			.collect(Collectors.toSet());
		assertThat(set).hasSize(iterations);
	}

}
