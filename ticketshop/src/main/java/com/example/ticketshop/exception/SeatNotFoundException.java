package com.example.ticketshop.exception;

public class SeatNotFoundException extends NotFoundException {
    public SeatNotFoundException(Long id) {
        super("Seat reservation with id " + id + " was not found");
    }
}
