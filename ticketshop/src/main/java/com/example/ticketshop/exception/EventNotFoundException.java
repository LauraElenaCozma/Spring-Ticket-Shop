package com.example.ticketshop.exception;

public class EventNotFoundException extends NotFoundException{
    public EventNotFoundException(Long id) {
        super("Event with id " + id + " was not found");
    }
    public EventNotFoundException() {
        super("The event of this order was not found");
    }
}
