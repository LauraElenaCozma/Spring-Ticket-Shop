package com.example.ticketshop.service;

import com.example.ticketshop.exception.NotAvailableSeatsException;
import com.example.ticketshop.exception.SeatNotFoundException;
import com.example.ticketshop.model.SeatReservation;
import com.example.ticketshop.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final EventService eventService;
    public ReservationService(ReservationRepository reservationRepository, EventService eventService) {
        this.reservationRepository = reservationRepository;
        this.eventService = eventService;
    }

    public SeatReservation createReservation(SeatReservation reservation) {
        Long eventId = getEventOfReservation(reservation);
        Integer numAvailable = eventService.getNumberOfAvailableSeats(eventId);
        if(numAvailable > 0)
            return reservationRepository.save(reservation);
        else throw new NotAvailableSeatsException();
    }

    public SeatReservation getReservation(Long id) {
        Optional<SeatReservation> reservation = reservationRepository.findById(id);
        if(reservation.isPresent())
            return reservation.get();
        else throw new SeatNotFoundException(id);
    }

    public List<SeatReservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    private Long getEventOfReservation(SeatReservation reservation) {
        return reservation.getOrder().getEvent().getId();
    }
}
