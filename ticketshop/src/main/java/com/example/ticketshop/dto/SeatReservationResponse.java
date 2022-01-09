package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatReservationResponse {
    private Long id;

    private Long orderId;

    private String rowNumber;

    private Integer seatNumber;
}
