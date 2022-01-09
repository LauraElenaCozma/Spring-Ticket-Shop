package com.example.ticketshop.exception;

public class ClientNotFoundException extends NotFoundException{

    public ClientNotFoundException(Long id) {
        super("Client with id " + id + " was not found");
    }
}
