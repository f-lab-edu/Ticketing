package com.ticketing.server.movie.application;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.server.global.config.WithAuthUser;
import com.ticketing.server.movie.application.request.MovieDeleteRequest;
import com.ticketing.server.movie.application.request.MovieRegisterRequest;
import com.ticketing.server.user.domain.UserGrade.ROLES;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
public class MovieControllerTest {

	@Autowired
	ObjectMapper mapper;

	@Autowired
	WebApplicationContext context;

	MockMvc mvc;

	JSONParser jsonParser = new JSONParser();

	private static final String MOVIES_URL = "/api/movies";

	private static final Long RUNNING_TIME = 100L;
	private static final String MOVIE_TITLE = "등록할 영화";

	private static final String TITLE = "$.title";
	private static final String MOVIE_DTOS = "$.movieDtos";

	@BeforeEach
	void init() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();
	}

	@Test
	@DisplayName("영화 등록 성공")
	@WithAuthUser(email = "staff@ticketing.com", role = ROLES.STAFF)
	void movieRegisterSuccess() throws Exception {

		MovieRegisterRequest request = new MovieRegisterRequest(MOVIE_TITLE, RUNNING_TIME);

		ResultActions resultActions = mvc.perform(post(MOVIES_URL)
			.content(mapper.writeValueAsString(request))
			.contentType(MediaType.APPLICATION_JSON));

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath(TITLE).value(MOVIE_TITLE));

	}

	@Test
	@DisplayName("영화 등록 실패 - 권한 부족")
	@WithAuthUser(email = "user@ticketing.com", role = ROLES.USER)
	void movieRegisterFailWithLowAuthority() throws Exception {

		MovieRegisterRequest request = new MovieRegisterRequest(MOVIE_TITLE, RUNNING_TIME);

		ResultActions resultActions = mvc.perform(post(MOVIES_URL)
			.content(mapper.writeValueAsString(request))
			.contentType(APPLICATION_JSON));

		resultActions.andExpect(status().isForbidden());

	}

	@Test
	@DisplayName("영화 등록 실패 - 인자값 검증 실패")
	@WithAuthUser(email = "staff@ticketing.com", role = ROLES.ADMIN)
	void movieRegisterFailWithWrongParameter() throws Exception {

		MovieRegisterRequest requestWithNullRunningTime = new MovieRegisterRequest(MOVIE_TITLE, null);

		// 1. 상영 시간 null
		ResultActions resultActions = mvc.perform(post(MOVIES_URL)
			.content(mapper.writeValueAsString(requestWithNullRunningTime))
			.contentType(APPLICATION_JSON));

		resultActions.andExpect(status().isBadRequest());

		// 2. 영화 제목 null
		MovieRegisterRequest requestWithNullTitle = new MovieRegisterRequest(null, RUNNING_TIME);

		resultActions = mvc.perform(post(MOVIES_URL)
			.content(mapper.writeValueAsString(requestWithNullTitle))
			.contentType(APPLICATION_JSON));

		resultActions.andExpect(status().isBadRequest());

		// 3. 영화 제목 ""
		MovieRegisterRequest requestWithoutTitle = new MovieRegisterRequest("", RUNNING_TIME);

		resultActions = mvc.perform(post(MOVIES_URL)
			.content(mapper.writeValueAsString(requestWithoutTitle))
			.contentType(APPLICATION_JSON));

		resultActions.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("영화 등록 실패 - 같은 영화 중복 등록")
	@WithAuthUser(email = "staff@ticketing.com", role = ROLES.ADMIN)
	void movieRegisterFailWithSameMovie() throws Exception {

		// given
		MovieRegisterRequest request = new MovieRegisterRequest(MOVIE_TITLE, RUNNING_TIME);

		mvc.perform(post(MOVIES_URL)
			.content(mapper.writeValueAsString(request))
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk());

		// when
		ResultActions resultActions = mvc.perform(post(MOVIES_URL)
			.content(mapper.writeValueAsString(request))
			.contentType(APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isConflict());

	}

	@Test

	@DisplayName("영화 삭제 성공")
	@WithAuthUser(email = "staff@ticketing.com", role = ROLES.ADMIN)
	void movieDeleteSuccess() throws Exception {

		// given
		MovieRegisterRequest request = new MovieRegisterRequest(MOVIE_TITLE, RUNNING_TIME);

		mvc.perform(post(MOVIES_URL)
				.content(mapper.writeValueAsString(request))
				.contentType(APPLICATION_JSON))
			.andExpect(status().isOk());

		// when
		// 1. 영화 조회 - 삭제할 영화 ID 뽑기
		ResultActions resultActions = mvc.perform(get(MOVIES_URL)
				.contentType(APPLICATION_JSON));
		resultActions
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath(MOVIE_DTOS).isNotEmpty());

		MvcResult result = resultActions.andReturn();
		Object obj = jsonParser.parse(result.getResponse().getContentAsString());
		JSONObject jsonObject = (JSONObject) obj;

		Object object =  jsonObject.get("movieDtos");
		JSONArray jsonArray = (JSONArray) object;
		JSONObject jsonObj = (JSONObject) jsonArray.get(0);

		Long movieId = (Long) jsonObj.get("movieId");

		// 2. 영화 삭제 - 해당 ID
		MovieDeleteRequest movieDeleteRequest = new MovieDeleteRequest(movieId);

		mvc.perform(delete(MOVIES_URL)
				.content(mapper.writeValueAsString(movieDeleteRequest))
				.contentType(APPLICATION_JSON))
			.andExpect(status().isOk());

		// then - 삭제한 영화랑 같은 제목의 영화 등록이 성공하는지 확인
		mvc.perform(post(MOVIES_URL)
				.content(mapper.writeValueAsString(request))
				.contentType(APPLICATION_JSON))
			.andExpect(status().isOk());
	}

}
