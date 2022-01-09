package com.example.ticketshop.mapper;

import com.example.ticketshop.dto.OrderRequest;
import com.example.ticketshop.dto.OrderResponse;
import com.example.ticketshop.dto.SeatReservationResponse;
import com.example.ticketshop.model.Client;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.service.ClientService;
import com.example.ticketshop.service.EventService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final EventService eventService;
    private final ClientService clientService;
    private final ReservationMapper reservationMapper;

    public OrderMapper(EventService eventService, ClientService clientService, ReservationMapper reservationMapper) {
        this.eventService = eventService;
        this.clientService = clientService;
        this.reservationMapper = reservationMapper;
    }

    public Order toEntity(OrderRequest orderRequest) {
        Event event = null;
        if(orderRequest.getEventId() != null)
            event = eventService.getEvent(orderRequest.getEventId());
        Client client = null;
        if(orderRequest.getClientId() != null)
            client = clientService.getClient(orderRequest.getClientId());
        return Order.builder()
                .event(event)
                .client(client)
                .orderDate(new Date())
                .build();
    }

    public OrderResponse toDtoRespose(Order order) {
        Long idEvent = null;
        if(order.getEvent() != null)
            idEvent = order.getEvent().getId();

        Long idClient = null;
        if(order.getClient() != null)
            idClient = order.getClient().getId();

        List<SeatReservationResponse> seats = new ArrayList<>();
        if(order.getSeatReservations() != null)
            seats = order.getSeatReservations().stream()
                    .map(reservationMapper::toDtoResponse)
                    .collect(Collectors.toList());
        return OrderResponse.builder()
                .id(order.getId())
                .eventId(idEvent)
                .clientId(idClient)
                .seatReservations(seats)
                .orderDate(order.getOrderDate())
                .build();
    }
}