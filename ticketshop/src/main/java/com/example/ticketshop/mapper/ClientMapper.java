package com.example.ticketshop.mapper;

import com.example.ticketshop.dto.ClientRequest;
import com.example.ticketshop.dto.ClientResponse;
import com.example.ticketshop.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client toEntity(ClientRequest clientRequest) {
        return Client.builder()
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .email(clientRequest.getEmail())
                .phoneNumber(clientRequest.getPhoneNumber())
                .build();
    }

    public ClientResponse toDtoResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }
}
