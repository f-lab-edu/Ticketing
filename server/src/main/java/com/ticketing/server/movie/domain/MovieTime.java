package com.ticketing.server.movie.domain;

import com.ticketing.server.global.dto.repository.AbstractEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MovieTime extends AbstractEntity {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "movie_id", referencedColumnName = "id", updatable = false)
	private Movie movie;

	@NotNull
	@ManyToOne
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

	private MovieTime(Movie movie, Theater theater, int round, LocalDateTime startAt, LocalDateTime endAt) {
		this.movie = movie;
		this.theater = theater;
		this.round = round;
		this.startAt = startAt;
		this.endAt = endAt;
	}

	public static MovieTime of(Movie movie, Theater theater, int round, LocalDateTime startAt) {
		Long runningTime = movie.getRunningTime();
		LocalDateTime endAt = startAt.plusMinutes(runningTime);

		return new MovieTime(movie, theater, round, startAt, endAt);
	}

	public List<Seat> getSeats() {
		return this.theater.getSeats();
	}

}
