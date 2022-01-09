package com.example.ticketshop.controller;

import com.example.ticketshop.dto.SeatReservationRequest;
import com.example.ticketshop.dto.SeatReservationResponse;
import com.example.ticketshop.mapper.ReservationMapper;
import com.example.ticketshop.model.SeatReservation;
import com.example.ticketshop.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
@Validated
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @PostMapping
    public ResponseEntity<SeatReservationResponse> createReservation(
            @Valid
            @RequestBody SeatReservationRequest reservationRequest) {
        SeatReservation reservation = reservationMapper.toEntity(reservationRequest);
        SeatReservation savedReservation = reservationService.createReservation(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                .body(reservationMapper.toDtoResponse(savedReservation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatReservationResponse> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(reservationMapper.toDtoResponse(reservationService.getReservation(id)));
    }

    @GetMapping
    public ResponseEntity<List<SeatReservationResponse>> getReservations() {
        List<SeatReservation> reservations = reservationService.getReservations();
        return ResponseEntity.ok()
                .body(reservations.stream().map(reservationMapper::toDtoResponse).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
