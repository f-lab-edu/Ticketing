package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.service.dto.MovieDTO;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieListResponse {

    @ApiModelProperty(value = "영화 제목")
    private List<MovieDTO> movieDtos;

}
