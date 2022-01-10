package com.example.ticketshop.service;

import com.example.ticketshop.exception.EventNotFoundException;
import com.example.ticketshop.exception.OrderNotFoundException;
import com.example.ticketshop.exception.PlayNotFoundException;
import com.example.ticketshop.model.*;
import com.example.ticketshop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;

    @Test
    void createOrderHappyFlow() {
        Order order = Order.builder()
                .client(Client.builder().firstName("Name").build())
                .event(Event.builder().price(100D).build())
                .build();
        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.createOrder(order);
        assertEquals(order.getClient().getFirstName(), result.getClient().getFirstName());
    }

    @Test
    void getAllOrdersHappyFlow() {
        Order order1 = Order.builder()
                .id(1L)
                .client(Client.builder().firstName("Name1").build())
                .event(Event.builder().price(101D).build())
                .build();
        Order order2 = Order.builder()
                .id(2L)
                .client(Client.builder().firstName("Name2").build())
                .event(Event.builder().price(102D).build())
                .build();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        List<Order> orders = orderService.getOrders();
        assertEquals(order1.getClient().getFirstName(),
                orders.get(0).getClient().getFirstName());
    }

    @Test
    void getOrderHappyFlow() {
        Long id = 1L;
        Order order = Order.builder()
                .id(id)
                .client(Client.builder().firstName("Name").build())
                .event(Event.builder().price(100D).build())
                .build();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Order result = orderService.getOrderById(id);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getClient().getFirstName(), result.getClient().getFirstName());
    }

    @Test
    void getOrderNegativeFlow() {
        Long id = 1L;
        String expected = "Order with id " + id + " was not found";
        OrderNotFoundException result = assertThrows(OrderNotFoundException.class,
                () -> orderService.getOrderById(id));
        assertEquals(expected, result.getMessage());
    }

    @Test
    void deleteOrderHappyFlow() {
        Long id = 1L;
        doNothing().when(orderRepository).deleteById(id);
        orderService.deleteOrder(id);
        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    void getPlayOfOrderHappyFlow() {
        Long id = 1L;
        Order order = Order.builder()
                .id(id)
                .event(Event.builder()
                        .price(100D)
                        .play(Play.builder()
                                .name("Play")
                                .genre(Genre.COMEDY)
                                .build())
                        .build())
                .build();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Play play = orderService.getPlayOfOrder(id);
        assertNotNull(play);
        assertEquals(order.getEvent().getPlay().getName(), play.getName());
        assertEquals(order.getEvent().getPlay().getGenre(), play.getGenre());
    }

    @Test
    void getPlayOfOrderEventNull() {
        Long id = 1L;
        Order order = Order.builder()
                .id(id)
                .event(null)
                .build();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        try {
            Play play = orderService.getPlayOfOrder(id);
        } catch (EventNotFoundException e) {
            assertEquals("The event of this order was not found", e.getMessage());
            verify(orderRepository, times(1)).findById(id);
        }
    }

    @Test
    void getPlayOfOrderPlayNull() {
        Long id = 1L;
        Order order = Order.builder()
                .id(id)
                .event(Event.builder()
                        .price(100D)
                        .play(null)
                        .build())
                .build();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        try {
            Play play = orderService.getPlayOfOrder(id);
        } catch (PlayNotFoundException e) {
            assertEquals("The play of this event was not found", e.getMessage());
            verify(orderRepository, times(1)).findById(id);
        }
    }

}
