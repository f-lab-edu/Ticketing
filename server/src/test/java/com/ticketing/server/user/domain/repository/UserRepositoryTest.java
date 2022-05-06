package com.ticketing.server.user.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
		User user = User.builder()
			.name("동효")
			.password("test")
			.email("test@test.com")
			.grade(UserGrade.GUEST)
			.phone("010-1234-5678")
			.build();

		// when
		userRepository.save(user);

		// then
		assertThat(user).isNotNull();
	}

}