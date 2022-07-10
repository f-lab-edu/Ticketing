package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.service.dto.MovieTimeDTO;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieTimeListResponse {

    @ApiModelProperty(value = "영화 시간표 정보")
    private List<MovieTimeDTO> movieTimeDTOS;

    public static MovieTimeListResponse from(List<MovieTimeDTO> movieTimeDTOS) {
        return new MovieTimeListResponse(movieTimeDTOS);
    }

}
