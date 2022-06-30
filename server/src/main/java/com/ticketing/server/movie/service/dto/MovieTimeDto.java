package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.domain.MovieTime;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MovieTimeDto {

    private long movieTimeId;

    private Integer theaterNumber;

    private Integer round;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    public static MovieTimeDto from(MovieTime movieTime) {
        return new MovieTimeDto(movieTime.getId(), movieTime.getTheater().getTheaterNumber(),
            movieTime.getRound(), movieTime.getStartAt(), movieTime.getEndAt());
    }

}
