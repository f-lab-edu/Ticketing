package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieTimeListDTO;
import java.time.LocalDate;

public interface MovieTimeService {

	MovieTimeListDTO getMovieTimes(Long movieId, LocalDate runningDate);

}
