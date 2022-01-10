package com.example.ticketshop.service;

import com.example.ticketshop.exception.ClientNotFoundException;
import com.example.ticketshop.model.Client;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;

    @Test
    void saveClientHappyFlow() {
        // arrange - define actions for mocks
        Client client = Client.builder()
                .firstName("Client first name")
                .lastName("Client last name")
                .email("client@gmail.com")
                .phoneNumber("0728180020")
                .build();
        when(clientRepository.save(client)).thenReturn(client);
        // act - call the inject mock method
        Client result = clientService.createClient(client);
        // assert - check the result based on arrange and act
        assertEquals(client.getFirstName(), result.getFirstName());
    }

    @Test
    void getAllClientsHappyFlow() {
        Client client1 = Client.builder()
                .firstName("Client1 first name")
                .lastName("Client1 last name")
                .email("client1@gmail.com")
                .phoneNumber("0718180020")
                .build();
        Client client2 = Client.builder()
                .firstName("Client2 first name")
                .lastName("Client2 last name")
                .email("client2@gmail.com")
                .phoneNumber("0728180020")
                .build();
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        //act
        List<Client> clients = clientService.getAllClients();

        assertEquals(client1.getFirstName(), clients.get(0).getFirstName());
        assertEquals(client2.getFirstName(), clients.get(1).getFirstName());
    }

    @Test
    void getClientHappyFlow() {
        // arrange
        Long id = 1L;
        Client client = Client.builder()
                .id(id)
                .firstName("Client first name")
                .lastName("Client last name")
                .email("client@gmail.com")
                .phoneNumber("0728180020")
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        // act
        Client result = clientService.getClient(id);
        // assert
        assertEquals(client.getFirstName(), result.getFirstName());

    }

    @Test
    void getClientNegativeFlow() {
        Long id = 1L;
        String expected = "Client with id " + id + " was not found";
        ClientNotFoundException result = assertThrows(ClientNotFoundException.class,
                () -> clientService.getClient(id));

        assertEquals(expected, result.getMessage());
    }

    @Test
    void deleteClientHappyFlow() {
        Long id = 1L;
        doNothing().when(clientRepository).deleteById(id);
        clientService.deleteClient(id);
        verify(clientRepository, times(1)).deleteById(id);
    }

//    @Test
//    void getOrdersOfClientHappyFlow() {
//        Long id = 1L;
//        Client client = Client.builder()
//                .id(id)
//                .firstName("Client first name")
//                .lastName("Client last name")
//                .email("client@gmail.com")
//                .phoneNumber("0728180020")
//                .build();
//
//        Order order1 = Order.builder()
//                .id(1L)
//                .client(client)
//                .event(Event.builder().build())
//                .build();
//        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
//        // act
//        Client result = clientService.getClient(id);
//    }
}
