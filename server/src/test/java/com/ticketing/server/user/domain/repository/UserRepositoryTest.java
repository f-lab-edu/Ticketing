package com.ticketing.server.user.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	void 유저레포지토리테스트() {
		// given
		User user = new User("유저1", "email@gmail.com", "testPassword01", UserGrade.GUEST, "010-1234-5678");

		// when
		userRepository.save(user);

		// then
		assertThat(user).isNotNull();
	}

}
