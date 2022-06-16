package com.ticketing.server.movie.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovieDto {

    @JsonProperty
    private String title;

}
