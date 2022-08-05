package com.ticketing.server.movie.aop;

import static com.ticketing.server.movie.domain.TicketLock.LOCK_VALUE;

import com.ticketing.server.global.exception.ErrorCode;
import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.movie.service.dto.TicketIdsDTO;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class TicketLockAspect {

	private final RedisTemplate<String, Object> redisTemplate;

	@Around("execution(* com.ticketing.server.movie.service.TicketLockService.*(..))")
	public Object ticketLock(ProceedingJoinPoint joinPoint) throws Throwable {
		List<String> ticketLockIds = getTicketLockIds(joinPoint);

		try {
			if (!isEveryTicketIdLock(ticketLockIds)) {
				throw new TicketingException(ErrorCode.BAD_REQUEST_TICKET_SOLD);
			}

			return joinPoint.proceed();
		} finally {
			ticketIdsUnlock(ticketLockIds);
		}
	}

	protected boolean isEveryTicketIdLock(List<String> ids) {
		for (String id : ids) {
			if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(id, LOCK_VALUE.getValue(), 5, TimeUnit.MINUTES))) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	protected Long ticketIdsUnlock(List<String> ids) {
		return redisTemplate.delete(ids);
	}

	private List<String> getTicketLockIds(ProceedingJoinPoint joinPoint) {
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof TicketIdsDTO) {
				TicketIdsDTO ids = (TicketIdsDTO) arg;
				return ids.makeTicketLockIds();
			}
		}

		throw new TicketingException(ErrorCode.EMPTY_TICKET_ID);
	}


}
