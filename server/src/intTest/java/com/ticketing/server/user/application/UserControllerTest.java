package com.ticketing.server.user.application;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.server.global.config.WithAuthUser;
import com.ticketing.server.user.application.request.LoginRequest;
import com.ticketing.server.user.application.request.SignUpRequest;
import com.ticketing.server.user.application.request.UserChangeGradeRequest;
import com.ticketing.server.user.application.request.UserChangePasswordRequest;
import com.ticketing.server.user.application.request.UserDeleteRequest;
import com.ticketing.server.user.domain.UserGrade;
import com.ticketing.server.user.domain.UserGrade.ROLES;
import com.ticketing.server.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class UserControllerTest {

	private static final String LOGIN_URL = "/api/auth/token";

	private static final String BASICS_URL = "/api/users";
	private static final String DETAILS_URL = "/api/users/details";
	private static final String CHANGE_PASSWORD_URL = "/api/users/password";
	private static final String CHANGE_GRADE_URL = "/api/users/grade";

	private static final String NAME = "$.name";
	private static final String EMAIL = "$.email";
	private static final String GRADE = "$.grade";
	private static final String PHONE = "$.phone";
	private static final String BEFORE_GRADE = "$.beforeGrade";
	private static final String AFTER_GRADE = "$.afterGrade";

	private static final String USER_EMAIL = "testemail@ticketing.com";
	private static final String USER_PW = "qwe123";
	private static final String USER_NAME = "?????????";
	private static final String USER_PHONE = "010-1234-5678";

	@Autowired
	UserRepository userRepository;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	WebApplicationContext context;

	MockMvc mvc;

	SignUpRequest signUpRequest;


	@Test
	@DisplayName("???????????? ??????")
	void registerSuccess() throws Exception {
		// given
		// when
		ResultActions resultActions = mvc.perform(
			post(BASICS_URL)
				.content(mapper.writeValueAsString(signUpRequest))
				.contentType(APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath(NAME).value(USER_NAME))
			.andExpect(jsonPath(EMAIL).value(USER_EMAIL));
	}

	@Test
	@DisplayName("?????? ?????? ??????")
	@WithAuthUser(email = USER_EMAIL, role = ROLES.USER)
	void detailsSuccess() throws Exception {
		// given
		mvc.perform(
			post(BASICS_URL)
				.content(mapper.writeValueAsString(signUpRequest))
				.contentType(APPLICATION_JSON)
		);

		// when
		ResultActions resultActions = mvc.perform(
			get(DETAILS_URL)
				.content(mapper.writeValueAsString(signUpRequest))
				.contentType(APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath(NAME).value(USER_NAME))
			.andExpect(jsonPath(EMAIL).value(USER_EMAIL))
			.andExpect(jsonPath(GRADE).value(UserGrade.USER.name()))
			.andExpect(jsonPath(PHONE).value(USER_PHONE));
	}

	@Test
	@DisplayName("?????? ?????? ??????")
	@WithAuthUser(email = USER_EMAIL, role = ROLES.USER)
	void deleteUserSuccess() throws Exception {
		// given
		UserDeleteRequest deleteRequest = new UserDeleteRequest(USER_EMAIL, USER_PW);
		LoginRequest loginRequest = new LoginRequest(USER_EMAIL, USER_PW);
		mvc.perform(
			post(BASICS_URL)
				.content(mapper.writeValueAsString(signUpRequest))
				.contentType(APPLICATION_JSON)
		);

		// when

		// 1. ?????? ?????? ??????
		mvc.perform(
			delete(BASICS_URL)
				.content(mapper.writeValueAsString(deleteRequest))
				.contentType(APPLICATION_JSON)
		);

		// 2. ????????? ?????? ?????????
		ResultActions resultActions = mvc.perform(post(LOGIN_URL)
			.content(mapper.writeValueAsString(loginRequest))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("???????????? ?????? ??????")
	@WithAuthUser(email = USER_EMAIL, role = ROLES.USER)
	void changePasswordSuccess() throws Exception {
		// given
		UserChangePasswordRequest changePasswordRequest = new UserChangePasswordRequest(USER_PW, "qwe1234");
		LoginRequest loginRequest = new LoginRequest(USER_EMAIL, USER_PW);
		mvc.perform(
			post(BASICS_URL)
				.content(mapper.writeValueAsString(this.signUpRequest))
				.contentType(APPLICATION_JSON)
		);

		// when

		// 1. ???????????? ??????
		mvc.perform(
				put(CHANGE_PASSWORD_URL)
					.content(mapper.writeValueAsString(changePasswordRequest))
					.contentType(APPLICATION_JSON)
			)
			.andExpect(status().isOk());

		// 2. ?????? ??? ???????????? ?????????
		ResultActions resultActions = mvc.perform(post(LOGIN_URL)
			.content(mapper.writeValueAsString(loginRequest))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("?????? ?????? ??????")
	@WithAuthUser(email = "admin@ticketing.com", role = ROLES.ADMIN)
	void changeGradeSuccess() throws Exception {
		// given
		UserChangeGradeRequest changeGradeRequest = new UserChangeGradeRequest(USER_EMAIL, UserGrade.STAFF);
		mvc.perform(
			post(BASICS_URL)
				.content(mapper.writeValueAsString(signUpRequest))
				.contentType(APPLICATION_JSON)
		);

		// when
		ResultActions resultActions = mvc.perform(
			post(CHANGE_GRADE_URL)
				.content(mapper.writeValueAsString(changeGradeRequest))
				.contentType(APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath(EMAIL).value(USER_EMAIL))
			.andExpect(jsonPath(BEFORE_GRADE).value(UserGrade.USER.name()))
			.andExpect(jsonPath(AFTER_GRADE).value(UserGrade.STAFF.name()));
	}

	@Test
	@DisplayName("?????? ?????? ?????? ?????? - ?????? ????????? ?????? ??????")
	@WithAuthUser(email = "staff@ticketing.com", role = ROLES.STAFF)
	void changeGradeFail() throws Exception {
		// given
		UserChangeGradeRequest changeGradeRequest = new UserChangeGradeRequest(USER_EMAIL, UserGrade.STAFF);

		// when
		ResultActions resultActions = mvc.perform(
			post(CHANGE_GRADE_URL)
				.content(mapper.writeValueAsString(changeGradeRequest))
				.contentType(APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isForbidden());
	}

	@BeforeEach
	void init() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();

		signUpRequest = new SignUpRequest(USER_NAME, USER_EMAIL, USER_PW, USER_PHONE);
	}

}
