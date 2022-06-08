package com.ticketing.server.global.redis;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshRedisRepository extends CrudRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByEmail(String email);

}
