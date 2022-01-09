package com.example.ticketshop.controller;

import com.example.ticketshop.dto.ClientRequest;
import com.example.ticketshop.dto.ClientResponse;
import com.example.ticketshop.dto.OrderResponse;
import com.example.ticketshop.dto.PlayResponse;
import com.example.ticketshop.mapper.ClientMapper;
import com.example.ticketshop.mapper.OrderMapper;
import com.example.ticketshop.mapper.PlayMapper;
import com.example.ticketshop.model.Client;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.service.ClientService;
import com.example.ticketshop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
@Validated
public class ClientController {

    public final ClientService clientService;
    public final OrderService orderService;
    public final ClientMapper clientMapper;
    public final OrderMapper orderMapper;
    public final PlayMapper playMapper;

    public ClientController(ClientService clientService, OrderService orderService, ClientMapper clientMapper, OrderMapper orderMapper, PlayMapper playMapper) {
        this.clientService = clientService;
        this.orderService = orderService;
        this.clientMapper = clientMapper;
        this.orderMapper = orderMapper;
        this.playMapper = playMapper;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(
            @Valid
            @RequestBody ClientRequest clientRequest) {
        Client client = clientMapper.toEntity(clientRequest);
        Client savedClient = clientService.createClient(client);
        return ResponseEntity.created(URI.create("/clients/" + savedClient.getId()))
                .body(clientMapper.toDtoResponse(savedClient));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable Long id) {
        Client returnedClient = clientService.getClient(id);
        return ResponseEntity.ok().body(clientMapper.toDtoResponse(returnedClient));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        List<ClientResponse> clientResponses = clientService.getAllClients().stream()
                .map(clientMapper::toDtoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(clientResponses);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponse>> getOrderOfClientByYearOrMonth(@PathVariable Long id,
                                                                @RequestParam(required = false) Integer year,
                                                                @RequestParam(required = false) Integer month) {
        List<OrderResponse> orders = clientService.getOrdersOfClientByYearOrMonth(id, year, month).stream()
                .map(orderMapper::toDtoRespose)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(orders);
    }

    @GetMapping("/{id}/plays")
    public ResponseEntity<List<PlayResponse>> getPlaysOfClient(@PathVariable Long id) {
        List<Order> orders = clientService.getOrdersOfClient(id);
        List<Play> plays = new ArrayList<>();
        for(Order ord : orders) {
            Play play = orderService.getPlayOfOrder(ord.getId());
            plays.add(play);
        }
        List<PlayResponse> returnedPlays = plays.stream()
                .distinct()
                .map(playMapper::toDtoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(returnedPlays);
    }
}
