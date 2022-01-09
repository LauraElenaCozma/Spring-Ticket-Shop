package com.example.ticketshop.exception;

public class VenueNotFoundException extends NotFoundException{
    public VenueNotFoundException(Long id) {
        super("Venue with id " + id + " was not found");
    }
}