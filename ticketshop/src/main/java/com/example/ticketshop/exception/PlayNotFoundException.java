package com.example.ticketshop.exception;


public class PlayNotFoundException extends NotFoundException {
    public PlayNotFoundException(Long id) {
        super("Play with id " + id + " was not found");
    }

    public PlayNotFoundException() {
        super("The play of this event was not found");
    }
}
