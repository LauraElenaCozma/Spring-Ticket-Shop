package com.example.ticketshop.service;

import com.example.ticketshop.exception.EventNotFoundException;
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

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent())
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
        if(order.getEvent() != null) {
            Play play = order.getEvent().getPlay();
            if(play != null) {
                return play;
            } else {
                throw new PlayNotFoundException();
            }
        } else {
            throw new EventNotFoundException();
        }
    }
}
