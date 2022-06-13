package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieDTO;
import java.util.List;

public interface MovieService {

    List<MovieDTO> getMovies();

}
