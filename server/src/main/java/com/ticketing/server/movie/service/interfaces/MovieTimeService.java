package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieTimeDTO;
import java.time.LocalDate;
import java.util.List;

public interface MovieTimeService {

    List<MovieTimeDTO> getMovieTimes(String title, LocalDate runningDate);

}
