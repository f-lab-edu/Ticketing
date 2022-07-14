package com.ticketing.server.movie.domain;


import com.ticketing.server.global.dto.repository.AbstractEntity;
import com.ticketing.server.movie.service.dto.MovieTimeDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieTime extends AbstractEntity {

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", referencedColumnName = "id", updatable = false)
	private Movie movie;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "theater_id", referencedColumnName = "id", updatable = false)
	private Theater theater;

	@NotNull
	private Integer round;

	@NotNull
	private LocalDateTime startAt;

	@NotNull
	private LocalDateTime endAt;

	@OneToMany(mappedBy = "movieTime", cascade = CascadeType.ALL)
	private List<Ticket> tickets = new ArrayList<>();

	public MovieTime(Movie movie, Theater theater, int round, LocalDateTime startAt) {
		this.movie = movie;
		this.theater = theater;
		this.round = round;
		this.startAt = startAt;
		this.endAt = generateEndAt(startAt);
	}

	MovieTime(Long id, Movie movie, Theater theater, int round, LocalDateTime startAt) {
		this.id = id;
		this.movie = movie;
		this.theater = theater;
		this.round = round;
		this.startAt = startAt;
		this.endAt = generateEndAt(startAt);
	}

	private LocalDateTime generateEndAt(LocalDateTime startAt) {
		Long runningTime = movie.getRunningTime();
		return startAt.plusMinutes(runningTime);
	}

	public String getMovieTitle() {
		return this.movie.getTitle();
	}

	public List<Seat> getSeats() {
		return this.theater.getSeats();
	}

	public MovieTimeDTO toMovieTimeDTO() {
		return new MovieTimeDTO(
			this.id, this.theater.getTheaterNumber(), this.round, this.startAt, this.endAt);
	}

}
