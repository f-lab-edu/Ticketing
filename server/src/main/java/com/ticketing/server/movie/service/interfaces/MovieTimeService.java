package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieTimeListDTO;
import com.ticketing.server.movie.service.dto.MovieTimeRegisterDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieTimeDTO;
import java.time.LocalDate;
import javax.validation.Valid;

public interface MovieTimeService {

	RegisteredMovieTimeDTO registerMovieTime(@Valid MovieTimeRegisterDTO movieTimeRegisterDto);

	MovieTimeListDTO getMovieTimes(Long movieId, LocalDate runningDate);

}
