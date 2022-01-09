package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class ActorRequest {
    @NotEmpty(message = "First name must not be empty!")
    private String firstName;
    @NotEmpty(message = "Last name must not be empty!")
    private String lastName;

    private Date dateOfBirth;

    private String placeOfBirth;
}
