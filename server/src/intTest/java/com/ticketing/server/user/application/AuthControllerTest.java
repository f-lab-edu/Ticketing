package com.ticketing.server.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.server.global.redis.RefreshRedisRepository;
import com.ticketing.server.user.application.request.LoginRequest;
import com.ticketing.server.user.application.request.RefreshRequest;
import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.response.TokenResponse;
import com.ticketing.server.user.service.interfaces.UserService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
class AuthControllerTest {

	private static final String LOGIN_URL = "/api/auth/token";
	private static final String REFRESH_URL = "/api/auth/refresh";
	private static final String LOGOUT_URL = "/api/auth/logout";
	private static final String REGISTER_URL = "/api/users";

	private static final String USER_EMAIL = "ticketing@gmail.com";
	private static final String USER_PW = "qwe123";

	@Autowired
	WebApplicationContext context;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RefreshRedisRepository refreshRedisRepository;

	MockMvc mvc;

	@Test
	@DisplayName("로그인 인증 성공")
	void loginSuccess() throws Exception {
		// given
		LoginRequest request = new LoginRequest(USER_EMAIL, USER_PW);

		// when
		ResultActions actions = mvc.perform(post(LOGIN_URL)
			.content(asJsonString(request))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("로그인 패스워드 인증 실패")
	void loginPasswordFail() throws Exception {
		// given
		LoginRequest request = new LoginRequest(USER_EMAIL, "qwe1234");

		// when
		ResultActions actions = mvc.perform(post(LOGIN_URL)
			.content(asJsonString(request))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("리프레쉬 토큰 발급 성공")
	void refreshTokenSuccess() throws Exception {
		// given
		LoginRequest loginRequest = new LoginRequest(USER_EMAIL, USER_PW);

		// when
		// 로그인
		String loginResponseBody = mvc.perform(post(LOGIN_URL)
				.content(asJsonString(loginRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andReturn()
			.getResponse()
			.getContentAsString();

		TokenResponse loginResponse = objectMapper.readValue(loginResponseBody, TokenResponse.class);
		RefreshRequest refreshRequest = new RefreshRequest(loginResponse.getRefreshToken());

		// 토큰재발급
		String refreshResponseBody = mvc.perform(post(REFRESH_URL)
				.content(asJsonString(refreshRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andReturn()
			.getResponse()
			.getContentAsString();

		TokenResponse refreshBody = objectMapper.readValue(refreshResponseBody, TokenResponse.class);

		// then
		assertAll(
			() -> assertThat(refreshBody.getAccessToken()).isNotEmpty(),
			() -> assertThat(refreshBody.getRefreshToken()).isNotEmpty(),
			() -> assertThat(loginResponse.getTokenType()).isEqualTo(refreshBody.getTokenType()),
			() -> assertThat(loginResponse.getExpiresIn()).isEqualTo(refreshBody.getExpiresIn())
		);
	}

	@Test
	@DisplayName("로그아웃 성공")
	void logoutSuccess() throws Exception {
		// given
		LoginRequest loginRequest = new LoginRequest(USER_EMAIL, USER_PW);

		// 로그인
		String loginResponseBody = mvc.perform(post(LOGIN_URL)
				.content(asJsonString(loginRequest))
				.contentType(MediaType.APPLICATION_JSON))
			.andReturn()
			.getResponse()
			.getContentAsString();

		TokenResponse loginResponse = objectMapper.readValue(loginResponseBody, TokenResponse.class);
		String authorization = loginResponse.getTokenType() + " " + loginResponse.getAccessToken();

		// 로그아웃
		ResultActions actions = mvc.perform(post(LOGOUT_URL)
			.header("Authorization", authorization));

		// then
		actions.andDo(print())
			.andExpect(status().isOk());
	}

	@BeforeEach
	void init() throws Exception {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();

		SignUpRequest signUpRequest = new SignUpRequest("ticketing", USER_EMAIL, USER_PW, "010-1234-5678");

		mvc.perform(post(REGISTER_URL)
			.content(asJsonString(signUpRequest))
			.contentType(MediaType.APPLICATION_JSON));
	}

	@AfterEach
	void tearDown() {
		refreshRedisRepository.deleteAll();
	}

	private String asJsonString(Object object) throws JsonProcessingException {

		return objectMapper.writeValueAsString(object);
	}

}
