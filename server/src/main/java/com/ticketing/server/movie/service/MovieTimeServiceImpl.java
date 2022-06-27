package com.ticketing.server.movie.service;

import static com.ticketing.server.global.exception.ErrorCode.MOVIE_NOT_FOUND;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.movie.domain.Movie;
import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.repository.MovieRepository;
import com.ticketing.server.movie.domain.repository.MovieTimeRepository;
import com.ticketing.server.movie.service.dto.MovieTimeDto;
import com.ticketing.server.movie.service.interfaces.MovieTimeService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieTimeServiceImpl implements MovieTimeService {

    private final MovieRepository movieRepository;

    private final MovieTimeRepository movieTimeRepository;

    @Override
    public List<MovieTimeDto> getMovieTimes(String title, LocalDate runningDate) {
        Movie movie = movieRepository.findByTitle(title)
            .orElseThrow(MovieTimeServiceImpl::throwMovieNotFound);

        List<MovieTime> movieTimes = movieTimeRepository.findByMovieAndRunningDate(movie, runningDate);

        return movieTimes.stream()
            .map(movieTime -> movieTime.toDto())
            .collect(Collectors.toList());

    }

    private static RuntimeException throwMovieNotFound() {
        throw new TicketingException(MOVIE_NOT_FOUND);
    }

}
