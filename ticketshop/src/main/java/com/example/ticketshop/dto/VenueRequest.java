package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class VenueRequest {
    @NotEmpty(message = "The venue name must not be null")
    private String venueName;

    @NotEmpty(message = "The location must not be null")
    private String locationName;

    @Min(value = 10, message = "The capacity must be bigger than 10")
    private Integer seatCapacity;

}
