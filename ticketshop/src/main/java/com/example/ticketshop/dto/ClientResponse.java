package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
