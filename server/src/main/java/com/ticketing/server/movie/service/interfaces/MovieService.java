package com.ticketing.server.movie.service.interfaces;

import com.ticketing.server.movie.service.dto.DeletedMovieDTO;
import com.ticketing.server.movie.service.dto.MovieListDTO;
import com.ticketing.server.movie.service.dto.RegisteredMovieDTO;

public interface MovieService {

	RegisteredMovieDTO registerMovie(String title, Long runningTime);

	MovieListDTO getMovies();

	DeletedMovieDTO deleteMovie(Long id);
}
