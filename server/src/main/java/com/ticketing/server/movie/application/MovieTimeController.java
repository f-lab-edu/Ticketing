package com.ticketing.server.movie.application;

import static com.ticketing.server.user.domain.UserGrade.ROLES.STAFF;

import com.ticketing.server.movie.application.request.MovieTimeRegisterRequest;
import com.ticketing.server.movie.application.response.MovieTimeInfoResponse;
import com.ticketing.server.movie.application.response.MovieTimeListResponse;
import com.ticketing.server.movie.service.dto.MovieTimeListDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieTimeDTO;
import com.ticketing.server.movie.service.interfaces.MovieTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping
	@ApiOperation(value = "영화 시간표 등록")
	@Secured(STAFF)
	public ResponseEntity<MovieTimeInfoResponse> registerMovieTime(
		@RequestBody @Valid MovieTimeRegisterRequest movieTimeRegisterRequest) {
		RegisteredMovieTimeDTO registeredMovieTimeDto = movieTimeService.registerMovieTime(
			movieTimeRegisterRequest.toMovieTimeRegisterDTO()
		);

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				registeredMovieTimeDto.toResponse()
			);
	}

	@GetMapping
	@ApiOperation(value = "영화 시간표 조회")
	@Validated
	public ResponseEntity<MovieTimeListResponse> getMovieTimes(
		@ApiParam(value = "영화 ID", required = true) @RequestParam @NotNull Long movieId,
		@ApiParam(value = "상영 날짜", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate runningDate) {
		MovieTimeListDTO movieTimeListDto = movieTimeService.getMovieTimes(movieId, runningDate);

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				movieTimeListDto.toResponse()
			);
	}

}
