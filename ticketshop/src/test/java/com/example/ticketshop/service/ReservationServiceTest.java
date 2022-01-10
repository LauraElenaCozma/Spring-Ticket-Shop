package com.example.ticketshop.service;

import com.example.ticketshop.exception.NotAvailableSeatsException;
import com.example.ticketshop.exception.SeatNotFoundException;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.model.SeatReservation;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private EventService eventService;
    @Test
    void createReservationHappyFlow() {
        Long eventId = 1L;
        Event event1 = Event.builder()
                .id(eventId)
                .price(50D)
                .hour("20:00")
                .venue(Venue.builder().seatCapacity(10).build())
                .build();
        SeatReservation reservation = SeatReservation.builder()
                .rowNumber("A")
                .seatNumber(1)
                .order(Order.builder().event(event1).build())
                .build();
        when(eventService.getNumberOfAvailableSeats(eventId)).thenReturn(9);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        SeatReservation result = reservationService.createReservation(reservation);
        assertEquals(reservation.getRowNumber(), result.getRowNumber());
        assertEquals(reservation.getSeatNumber(), result.getSeatNumber());
    }

    @Test
    void createReservationNegativeFlow() {
        Long eventId = 1L;
        Event event1 = Event.builder()
                .id(eventId)
                .price(50D)
                .hour("20:00")
                .venue(Venue.builder().seatCapacity(10).build())
                .build();
        SeatReservation reservation = SeatReservation.builder()
                .rowNumber("A")
                .seatNumber(1)
                .order(Order.builder().event(event1).build())
                .build();
        when(eventService.getNumberOfAvailableSeats(eventId)).thenReturn(0);
        try {
            reservationService.createReservation(reservation);
        } catch (NotAvailableSeatsException e) {
            assertEquals("There are no available seats at the reserved event!", e.getMessage());
            verify(reservationRepository, times(0)).save(reservation);
        }
    }

    @Test
    void getAllReservationsHappyFlow() {
        SeatReservation reservation1 = SeatReservation.builder()
                .id(1L)
                .rowNumber("A")
                .seatNumber(1)
                .build();
        SeatReservation reservation2 = SeatReservation.builder()
                .id(2L)
                .rowNumber("B")
                .seatNumber(2)
                .build();
        when(reservationRepository.findAll())
                .thenReturn(Arrays.asList(reservation1, reservation2));
        List<SeatReservation> reservations = reservationService.getReservations();
        assertEquals(reservation1.getSeatNumber(), reservations.get(0).getSeatNumber());
        assertEquals(reservation2.getSeatNumber(), reservations.get(1).getSeatNumber());
    }

    @Test
    void getReservationHappyFlow() {
        Long id = 1L;
        SeatReservation reservation = SeatReservation.builder()
                .id(1L)
                .rowNumber("A")
                .seatNumber(1)
                .build();
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        SeatReservation result = reservationService.getReservation(id);
        assertEquals(reservation.getSeatNumber(), result.getSeatNumber());
        assertEquals(reservation.getId(), result.getId());
    }

    @Test
    void getReservationNegativeFlow() {
        Long id = 1L;
        String expected = "Seat reservation with id " + id + " was not found";
        SeatNotFoundException exception = assertThrows(SeatNotFoundException.class,
                () -> reservationService.getReservation(id));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void deleteReservation() {
        Long id = 1L;
        doNothing().when(reservationRepository).deleteById(id);
        reservationService.deleteReservation(id);
        verify(reservationRepository, times(1)).deleteById(id);
    }
}
