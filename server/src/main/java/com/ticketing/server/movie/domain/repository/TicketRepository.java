package com.ticketing.server.movie.domain.repository;

import com.ticketing.server.movie.domain.MovieTime;
import com.ticketing.server.movie.domain.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query(
		value = "SELECT t "
			+ "FROM Ticket t "
			+ "JOIN FETCH t.seat s "
			+ "WHERE t.movieTime = :movieTime "
			+ "AND t.deletedAt IS NULL"
	)
	List<Ticket> findValidTickets(MovieTime movieTime);

	@Query(
		value =
			"SELECT t "
			+ "FROM Ticket t "
			+ "JOIN FETCH t.movieTime mt "
			+ "JOIN FETCH t.seat s "
			+ "JOIN FETCH s.theater th "
			+ "WHERE t.paymentId = :paymentId "
	)
	List<Ticket> findTicketFetchJoinByPaymentId(@Param("paymentId") Long paymentId);

	@Query(
		value =
			"SELECT t "
				+ "FROM Ticket t "
				+ "JOIN FETCH t.movieTime mt "
				+ "JOIN FETCH t.seat s "
				+ "JOIN FETCH s.theater th "
				+ "WHERE t.id IN (:ticketIds) "
	)
	List<Ticket> findTicketFetchJoinByTicketIds(@Param("ticketIds") List<Long> ticketIds);

}
