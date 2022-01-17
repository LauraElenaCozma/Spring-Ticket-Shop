package com.example.ticketshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date orderDate;
}
