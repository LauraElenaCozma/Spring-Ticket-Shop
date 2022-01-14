package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;

    private Long eventId;

    private Long clientId;

    private Integer numReservedSeats;

    private Date orderDate;
}
