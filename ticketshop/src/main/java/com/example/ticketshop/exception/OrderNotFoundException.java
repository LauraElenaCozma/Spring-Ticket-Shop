package com.example.ticketshop.exception;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order with id " + id + " was not found");
    }
}
