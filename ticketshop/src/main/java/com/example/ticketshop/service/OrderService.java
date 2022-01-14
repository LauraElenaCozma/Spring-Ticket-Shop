package com.example.ticketshop.service;

import com.example.ticketshop.exception.EventNotFoundException;
import com.example.ticketshop.exception.NotAvailableSeatsException;
import com.example.ticketshop.exception.OrderNotFoundException;
import com.example.ticketshop.exception.PlayNotFoundException;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final EventService eventService;

    public OrderService(OrderRepository orderRepository, EventService eventService) {
        this.orderRepository = orderRepository;
        this.eventService = eventService;
    }

    public Order createOrder(Order order) {
        Long eventId = order.getEvent().getId();
        Integer numAvailable = eventService.getNumberOfAvailableSeats(eventId);
        if (numAvailable - order.getNumReservedSeats() >= 0)
            return orderRepository.save(order);
        else throw new NotAvailableSeatsException();
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent())
            return order.get();
        else throw new OrderNotFoundException(id);
    }


    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Play getPlayOfOrder(Long id) {
        Order order = getOrderById(id);
        if (order.getEvent() != null) {
            Play play = order.getEvent().getPlay();
            if (play != null) {
                return play;
            } else {
                throw new PlayNotFoundException();
            }
        } else {
            throw new EventNotFoundException();
        }
    }

    public Order updateReservedSeatsOfOrder(Long id, Integer numReservedSeats) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new OrderNotFoundException(id);
        Order savedOrder = order.get();
        Long eventId = savedOrder.getEvent().getId();
        Integer numAvailable = eventService.getNumberOfAvailableSeats(eventId);
        if (numAvailable - numReservedSeats < 0)
            throw new NotAvailableSeatsException();
        savedOrder.setNumReservedSeats(numReservedSeats);
        return orderRepository.save(savedOrder);
    }
}
