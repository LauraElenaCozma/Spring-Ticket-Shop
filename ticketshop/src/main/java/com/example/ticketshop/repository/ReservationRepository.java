package com.example.ticketshop.repository;

import com.example.ticketshop.model.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<SeatReservation, Long> {
}
