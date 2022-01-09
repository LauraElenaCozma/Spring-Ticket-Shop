package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ActorResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String placeOfBirth;

}
