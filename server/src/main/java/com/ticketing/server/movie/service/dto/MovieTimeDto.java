package com.ticketing.server.movie.service.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MovieTimeDto {

    private long movieTimeId;

    private Integer theaterNumber;

    private Integer round;

    private LocalTime startAt;

    private LocalTime endAt;

}
