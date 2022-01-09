package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class SeatReservationRequest {
    @NotNull(message = "The order must not be empty")
    private Long orderId;

    @NotNull(message = "The row number must not be empty")
    private String rowNumber;

    @NotNull(message = "The seat number must not be empty")
    @Min(value = 1, message = "The seat number must be bigger than 1")
    private Integer seatNumber;
}
