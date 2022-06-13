package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieDto;
import java.util.List;

public interface MovieService {

    List<MovieDto> getMovies();

}
