package com.ticketing.server.movie.application;

import static com.ticketing.server.user.domain.UserGrade.ROLES.STAFF;

import com.ticketing.server.movie.application.request.MovieDeleteRequest;
import com.ticketing.server.movie.application.request.MovieRegisterRequest;
import com.ticketing.server.movie.application.response.MovieDeleteResponse;
import com.ticketing.server.movie.application.response.MovieListResponse;
import com.ticketing.server.movie.application.response.MovieInfoResponse;
import com.ticketing.server.movie.service.dto.DeletedMovieDTO;
import com.ticketing.server.movie.service.dto.MovieListDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieDTO;
import com.ticketing.server.movie.service.interfaces.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@Api(value = "Movie API", tags = {"Movie"})
@RequiredArgsConstructor
@Slf4j
public class MovieController {

	private final MovieService movieService;

	@PostMapping()
	@ApiOperation(value = "영화 정보 등록")
	@Secured(STAFF)
	public ResponseEntity<MovieInfoResponse> registerMovie(@RequestBody @Valid MovieRegisterRequest request) {
		RegisteredMovieDTO registeredMovieDto =
			movieService.registerMovie(request.getTitle(), request.getRunningTime());

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				registeredMovieDto.toResponse()
			);
	}

	@GetMapping()
	@ApiOperation(value = "영화 목록 조회")
	public ResponseEntity<MovieListResponse> getMovies() {
		MovieListDTO movieListDto = movieService.getMovies();

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				movieListDto.toResponse()
			);
	}

	@DeleteMapping()
	@ApiOperation(value = "영화 정보 삭제")
	@Secured(STAFF)
	public ResponseEntity<MovieDeleteResponse> deleteMovie(@RequestBody @Valid MovieDeleteRequest request) {
		DeletedMovieDTO deletedMovieDto = movieService.deleteMovie(request.getId());

		return ResponseEntity.status(HttpStatus.OK)
			.body(
				deletedMovieDto.toResponse()
			);
	}
}
