package com.ticketing.server.global.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RefreshRedisRepositoryTest {

	@Autowired
	RefreshRedisRepository refreshRedisRepository;

	@AfterEach
	void tearDown() {
		refreshRedisRepository.deleteAll();
	}

	@Test
	@DisplayName("기본 등록 및 조회기능")
	void saveAndFind() {
		// given
		RefreshToken refreshToken = new RefreshToken("ticketing@gmail.com", "refreshToken");

		// when
		refreshRedisRepository.save(refreshToken);

		// then
		RefreshToken findRefreshToken = refreshRedisRepository.findById(refreshToken.getId()).get();
		assertAll(
			() -> assertThat(findRefreshToken.getEmail()).isEqualTo("ticketing@gmail.com")
			, () -> assertThat(findRefreshToken.getToken()).isEqualTo("refreshToken")
		);
	}

	@Test
	@DisplayName("기본 등록 및 이메일 조회")
	void saveAndFindByEmail() {
		// given
		RefreshToken refreshToken = new RefreshToken("ticketing@gmail.com", "refreshToken");

		// when
		refreshRedisRepository.save(refreshToken);

		// then
		RefreshToken findRefreshToken = refreshRedisRepository.findByEmail(refreshToken.getEmail()).get();
		assertAll(
			() -> assertThat(findRefreshToken.getEmail()).isEqualTo("ticketing@gmail.com")
			, () -> assertThat(findRefreshToken.getToken()).isEqualTo("refreshToken")
		);
	}

	@Test
	@DisplayName("기본 등록 및 수정기능")
	void saveAndSave() {
		// given
		RefreshToken refreshToken = new RefreshToken("ticketing@gmail.com", "refreshToken");
		refreshRedisRepository.save(refreshToken);
		Long id = refreshToken.getId();

		// when
		RefreshToken savedRefreshToken = refreshRedisRepository.findById(id).get();
		savedRefreshToken.changeToken("refreshToken2");
		refreshRedisRepository.save(savedRefreshToken);

		// then
		RefreshToken lastSavedRefreshToken = refreshRedisRepository.findById(id).get();
		assertThat(lastSavedRefreshToken.getToken()).isEqualTo("refreshToken2");
	}

}
