package com.example.ticketshop.controller;

import com.example.ticketshop.dto.OrderRequest;
import com.example.ticketshop.dto.OrderResponse;
import com.example.ticketshop.mapper.OrderMapper;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid
            @RequestBody OrderRequest orderRequest) {
        Order savedOrder = orderService.createOrder(orderMapper.toEntity(orderRequest));
        return ResponseEntity.created(URI.create("/orders/" + savedOrder.getId()))
                .body(orderMapper.toDtoRespose(savedOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok().body(orderMapper.toDtoRespose(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok()
                .body(orderService.getOrders().stream()
                        .map(orderMapper::toDtoRespose)
                        .collect(Collectors.toList()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponse> updateReservedSeatsOfOrder(@PathVariable Long id,
                                                                   @RequestBody Integer numReservedSeats) {
        Order newOrder = orderService.updateReservedSeatsOfOrder(id, numReservedSeats);
        return ResponseEntity.ok().body(orderMapper.toDtoRespose(newOrder));
    }
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
