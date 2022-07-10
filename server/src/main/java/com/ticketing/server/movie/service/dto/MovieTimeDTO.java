package com.ticketing.server.movie.service.dto;

import com.ticketing.server.movie.domain.MovieTime;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieTimeDTO {

    private Long movieTimeId;

    private Integer theaterNumber;

    private Integer round;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    public static MovieTimeDTO from(MovieTime movieTime) {
        return new MovieTimeDTO(movieTime.getId(), movieTime.getTheater().getTheaterNumber(),
            movieTime.getRound(), movieTime.getStartAt(), movieTime.getEndAt());
    }

}
