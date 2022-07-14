package com.ticketing.server.global.redis;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PaymentCacheRepository extends CrudRepository<PaymentCache, Long> {

	Optional<PaymentCache> findByEmail(String email);

}
