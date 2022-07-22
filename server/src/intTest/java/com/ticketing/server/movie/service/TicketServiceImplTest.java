package com.ticketing.server.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketServiceImplTest {

	@Autowired
	private TicketServiceImpl ticketService;

	@Test
	@DisplayName("티켓 lock 동시성 체크")
	@SuppressWarnings({"java:S5960"})
	void ticketMultiThread() throws InterruptedException {
		// given
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(2);

		List<String> lockIds = List.of("TicketLock:1", "TicketLock:2", "TicketLock:3");
		AtomicBoolean result1 = new AtomicBoolean(Boolean.TRUE);
		AtomicBoolean result2 = new AtomicBoolean(Boolean.TRUE);

		// when
		executorService.execute(() -> {
			result1.set(ticketService.isEveryTicketIdLock(lockIds));
			latch.countDown();
		});

		executorService.execute(() -> {
			result2.set(ticketService.isEveryTicketIdLock(List.of("TicketLock:1")));
			latch.countDown();
		});

		latch.await();

		// then
		Long unlockCount = ticketService.ticketIdsUnlock(lockIds);

		assertAll(
			() -> assertThat(result1).isNotEqualTo(result2),
			() -> assertThat(unlockCount > 1).isTrue()
		);
	}

}
