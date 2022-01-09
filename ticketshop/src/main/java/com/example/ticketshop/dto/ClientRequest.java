package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ClientRequest {
    @NotEmpty(message = "The first name must not be empty")
    private String firstName;

    @NotEmpty(message = "The last name must not be empty")
    private String lastName;

    private String email;

    private String phoneNumber;
}
