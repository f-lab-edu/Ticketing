package com.ticketing.server.user.application;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.server.user.application.request.LoginRequest;
import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.service.interfaces.UserService;
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
class UserControllerTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ObjectMapper objectMapper;

	MockMvc mvc;

	@BeforeEach
	void init() throws Exception {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();

		SignUpRequest signUpRequest = new SignUpRequest("ticketing", "ticketing@gmail.com", "qwe123", "010-2240-7920");

		mvc.perform(post("/api/user")
			.content(asJsonString(signUpRequest))
			.contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@DisplayName("로그인 인증 성공")
	void loginSuccess() throws Exception {
		// given
		LoginRequest request = new LoginRequest("ticketing@gmail.com", "qwe123");

		// when
		ResultActions actions = mvc.perform(post("/api/user/login")
			.content(asJsonString(request))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().exists("ACCESS_TOKEN"))
			.andExpect(header().exists("REFRESH_TOKEN"));
	}

	@Test
	@DisplayName("로그인 패스워드 인증 실패")
	void loginPasswordFail() throws Exception {
		// given
		LoginRequest request = new LoginRequest("ticketing@gmail.com", "qwe1234");

		// when
		ResultActions actions = mvc.perform(post("/api/user/login")
			.content(asJsonString(request))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andDo(print())
			.andExpect(status().isUnauthorized());
	}

	private String asJsonString(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

}
