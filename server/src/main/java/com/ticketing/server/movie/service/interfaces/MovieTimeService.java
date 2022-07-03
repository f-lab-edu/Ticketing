package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieTimeDto;
import java.time.LocalDate;
import java.util.List;

public interface MovieTimeService {

    List<MovieTimeDto> getMovieTimes(String title, LocalDate runningDate);

}
