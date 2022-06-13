package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.movie.service.dto.MovieDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends AbstractEntity {

    @NotNull
    @Column(unique = true)
    private String title;

    @NotNull
    private Integer runningTime;

    public MovieDTO toDTO() {
        return new MovieDTO(this.getId(), this.title);
    }

}
