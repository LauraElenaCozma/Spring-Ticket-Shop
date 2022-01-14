package com.example.ticketshop.exception;

public class PhoneNotUniqueException extends RuntimeException {
    public PhoneNotUniqueException(String phone) {
        super("Phone number " + phone + " already exists in the database!");
    }
}
