package com.ticketing.server.movie.service;

import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.service.dto.MovieDto;
import com.ticketing.server.movie.service.interfaces.MovieService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public List<MovieDto> getMovies() {
        List<Movie> movies = movieRepository.findByDeletedAt(null);

        return movies.stream()
            .map(movie -> movie.toDto())
            .collect(Collectors.toList());

    }

}
