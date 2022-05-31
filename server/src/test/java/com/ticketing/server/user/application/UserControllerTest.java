package com.ticketing.server.user.application;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	MockMvc mvc;

	@BeforeEach
	void init() throws Exception {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();

		mvc.perform(post("/user")
			.content("{\n" +
				" \"email\": \"ticketing@gmail.com\",\n" +
				" \"password\": \"qwe123\",\n" +
				" \"name\": \"ticketing\",\n" +
				" \"phone\": \"010-2240-7920\"\n" +
				"}")
			.contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	@DisplayName("로그인 인증 성공")
	void loginSuccess() throws Exception {
		// given
		String requestJson = "{\n" +
			" \"email\": \"ticketing@gmail.com\",\n" +
			" \"password\": \"qwe123\"\n" +
			"}";

		// when
		ResultActions actions = mvc.perform(post("/user/login")
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().exists("ACCESS_TOKEN"));
	}

	@Test
	@DisplayName("로그인 패스워드 인증 실패")
	void loginPasswordFail() throws Exception {
		// given
		String requestJson = "{\n" +
			" \"email\": \"ticketing@gmail.com\",\n" +
			" \"password\": \"qwe1234\"\n" +
			"}";

		// when
		ResultActions actions = mvc.perform(post("/user/login")
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON));

		// then
		actions.andDo(print())
			.andExpect(status().isUnauthorized());
	}

}