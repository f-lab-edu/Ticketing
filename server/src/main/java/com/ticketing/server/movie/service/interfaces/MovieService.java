package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.MovieDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieDTO;
import java.util.List;

public interface MovieService {

	RegisteredMovieDTO registerMovie(String title, Long runningTime);

    List<MovieDTO> getMovies();
}
