package com.ticketing.server.movie.application;

import com.ticketing.server.movie.application.response.MovieListResponse;
import com.ticketing.server.movie.service.dto.MovieDTO;
import com.ticketing.server.movie.service.interfaces.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
@Api(value = "Movie API", tags = {"Movie"})
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/list")
    @ApiOperation(value = "영화 목록 조회")
    public ResponseEntity<MovieListResponse> getMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(MovieListResponse.from(movieService.getMovies()));
    }

}
