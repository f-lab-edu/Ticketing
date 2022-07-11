package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieDTO;
import com.ticketing.server.movie.service.dto.MovieRegisterDTO;
import java.util.List;

public interface MovieService {

	MovieDTO registerMovie(MovieRegisterDTO movieRegisterDto);

    List<MovieDTO> getMovies();

}
