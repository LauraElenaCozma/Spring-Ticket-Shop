package com.example.ticketshop.exception;

public class EmailNotUniqueException extends RuntimeException{
    public EmailNotUniqueException(String email) {
        super("The email " + email + " already exists in the database!");
    }
}
