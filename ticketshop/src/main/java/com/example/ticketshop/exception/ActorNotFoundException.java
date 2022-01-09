package com.example.ticketshop.exception;

public class ActorNotFoundException extends NotFoundException{
    public ActorNotFoundException(Long id) {
        super("Actor with id " + id + " was not found");
    }
}
