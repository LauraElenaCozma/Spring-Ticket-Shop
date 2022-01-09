package com.example.ticketshop.dto;

import com.example.ticketshop.model.SeatReservation;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;

    private Long eventId;

    private Long clientId;

    private List<SeatReservationResponse> seatReservations;

    private Date orderDate;
}
