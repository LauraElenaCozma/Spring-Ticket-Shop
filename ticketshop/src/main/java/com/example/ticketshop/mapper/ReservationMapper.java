package com.example.ticketshop.mapper;

import com.example.ticketshop.dto.SeatReservationRequest;
import com.example.ticketshop.dto.SeatReservationResponse;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.model.SeatReservation;
import com.example.ticketshop.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    private final OrderService orderService;

    public ReservationMapper(OrderService orderService) {
        this.orderService = orderService;
    }

    public SeatReservation toEntity(SeatReservationRequest seatRequest) {
        Order order = null;
        if (seatRequest.getOrderId() != null)
            order = orderService.getOrderById(seatRequest.getOrderId());
        return SeatReservation.builder()
                .order(order)
                .rowNumber(seatRequest.getRowNumber())
                .seatNumber(seatRequest.getSeatNumber())
                .build();
    }

    public SeatReservationResponse toDtoResponse(SeatReservation seatReservation) {
        Long orderId = null;
        if (seatReservation.getOrder() != null)
            orderId = seatReservation.getOrder().getId();
        return SeatReservationResponse.builder()
                .id(seatReservation.getId())
                .orderId(orderId)
                .rowNumber(seatReservation.getRowNumber())
                .seatNumber(seatReservation.getSeatNumber())
                .build();
    }
}
