package com.ticketing.server.movie.application;

import com.ticketing.server.movie.application.response.MovieTimeListResponse;
import com.ticketing.server.movie.service.interfaces.MovieTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movieTimes")
@Api(value = "MovieTime API", tags = {"Movie Time"})
@RequiredArgsConstructor
@Slf4j
public class MovieTimeController {

    private final MovieTimeService movieTimeService;

    @GetMapping
    @ApiOperation(value = "영화 시간표 조회")
    @Validated
    public ResponseEntity<MovieTimeListResponse> getMovieTimes(
        @ApiParam(value = "영화 제목", required = true) @RequestParam String title,
        @ApiParam(value = "상영 날짜", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate runningDate) {
        return ResponseEntity.status(HttpStatus.OK).body(MovieTimeListResponse.from(movieTimeService.getMovieTimes(title, runningDate)));
    }


}
