package com.example.ticketshop.repository;

import com.example.ticketshop.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT ev FROM Event ev " +
            "WHERE MONTH(ev.date) = :month AND YEAR(ev.date) = :year")
    List<Event> getAllEventsByMonthAndYear(@Param("month") Integer month,
                                           @Param("year") Integer year);

    @Query("SELECT ev FROM Event ev " +
            "JOIN Order o ON o.event.id = ev.id " +
            "JOIN SeatReservation seat ON seat.order.id = o.id " +
            "GROUP BY ev.id " +
            "ORDER BY COUNT(seat.id) DESC")
    List<Event> getTopEventBySoldTickets();

    @Query("SELECT COUNT(seat.id) FROM Event e " +
            "JOIN Order o ON o.event.id = e.id " +
            "JOIN SeatReservation seat ON seat.order.id = o.id " +
            "WHERE e.id = :eventId")
    Integer getNumberOfSoldSeats(@Param("eventId") Long eventId);
}
