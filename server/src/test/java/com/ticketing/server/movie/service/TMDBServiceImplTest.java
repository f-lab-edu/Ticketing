package com.ticketing.server.movie.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class TMDBServiceImplTest {

    @Value("${tmdb.api-key}")
    private String apiKey;

    @Value("${tmdb.read-access-token}")
    private String readAccessToken;

    @Autowired
    RestTemplate restTemplate;

    @Test
    @DisplayName("TMDB Service Test - Get [Now Playing] movies")
    void shouldAbleToGetMovieList() throws Exception {
        // given
        assertNotNull(apiKey);
        assertNotNull(readAccessToken);

        ArrayList<Charset> acceptCharset = new ArrayList<>();
        acceptCharset.add(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptCharset(acceptCharset);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(readAccessToken);

        Map<String, String> params = new HashMap<>();
        params.put("api_key", apiKey);
        params.put("language", "ko");

        HttpEntity<?> request = new HttpEntity<>(headers);

        // when
        ResponseEntity<?> response = restTemplate.exchange(
            "https://api.themoviedb.org/3/movie/now_playing?" + mapToUrlParam(params),
            HttpMethod.GET,
            request,
            String.class
        );

//        JSONParser parser  = new JSONParser();
//        Object obj = parser.parse(String.valueOf(response));
//        Object results = ((JSONObject) obj).get("results");
//
//        ArrayList<String> movieList = new ArrayList<>();
//
//        ArrayList<JSONObject> jsonMovieList = new ArrayList<>();

        // then
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    private static String mapToUrlParam(Map<String, String> params) {
        StringBuffer paramData = new StringBuffer();

        for(Map.Entry<String, String> param : params.entrySet()) {
            if(paramData.length() != 0) {
                paramData.append('&');
            }

            paramData.append(param.getKey());
            paramData.append('=');
            paramData.append(param.getValue());
        }

        return paramData.toString();
    }

}

