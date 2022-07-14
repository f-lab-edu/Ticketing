package com.ticketing.server.movie.service.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieTimeDTO {

    private Long movieTimeId;

    private Integer theaterNumber;

    private Integer round;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

}
