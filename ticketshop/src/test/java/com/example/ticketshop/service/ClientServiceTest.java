package com.example.ticketshop.service;

import com.example.ticketshop.exception.ClientNotFoundException;
import com.example.ticketshop.exception.EmailNotUniqueException;
import com.example.ticketshop.exception.PhoneNotUniqueException;
import com.example.ticketshop.model.Client;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void updateClientHappyFlow() {
        Long id = 1L;
        Client clientOld = Client.builder()
                .firstName("Client1 first name")
                .lastName("Client1 last name")
                .email("client1@gmail.com")
                .phoneNumber("0718180021")
                .orders(Arrays.asList(Order.builder().id(1L).build()))
                .build();
        Client clientNew = Client.builder()
                .firstName("Client2 first name")
                .lastName("Client2 last name")
                .email("client2@gmail.com")
                .phoneNumber("0728180022")
                .orders(null)
                .build();
        Client savedClient = Client.builder()
                .firstName("Client2 first name")
                .lastName("Client2 last name")
                .email("client2@gmail.com")
                .phoneNumber("0728180022")
                .orders(Arrays.asList(Order.builder().id(1L).build()))
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(clientOld));
        when(clientRepository.findByEmail(clientNew.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findByPhoneNumber(clientNew.getPhoneNumber())).thenReturn(Optional.empty());
        when(clientRepository.save(savedClient)).thenReturn(savedClient);

        Client result = clientService.updateClient(id, clientNew);
        assertEquals(savedClient.getFirstName(), result.getFirstName());
        assertEquals(clientNew.getEmail(), result.getEmail());
        assertNotNull(result.getOrders());
        assertEquals(savedClient.getOrders().get(0).getId(), result.getOrders().get(0).getId());
    }

    @Test
    void updateClientNotFound() {
        Long id = 1L;
        Client clientNew = Client.builder()
                .firstName("Client2 first name")
                .lastName("Client2 last name")
                .email("client2@gmail.com")
                .phoneNumber("0728180022")
                .orders(null)
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.empty());
        try {
            clientService.updateClient(id, clientNew);
        } catch (ClientNotFoundException e) {
            assertEquals("Client with id " + id + " was not found", e.getMessage());
            verify(clientRepository, never()).findByEmail(clientNew.getEmail());
            verify(clientRepository, never()).save(clientNew);
        }
    }

    @Test
    void updateClientEmailNotUnique() {
        Long id = 1L;
        Client clientOld = Client.builder()
                .firstName("Client1 first name")
                .lastName("Client1 last name")
                .email("client1@gmail.com")
                .phoneNumber("0718180021")
                .orders(Arrays.asList(Order.builder().id(1L).build()))
                .build();
        Client clientNew = Client.builder()
                .firstName("Client2 first name")
                .lastName("Client2 last name")
                .email("client2@gmail.com")
                .phoneNumber("0728180022")
                .orders(null)
                .build();
        Client foundClient = Client.builder()
                .firstName("Client3 first name")
                .lastName("Client3 last name")
                .email("client2@gmail.com")
                .phoneNumber("0728180023")
                .orders(null)
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(clientOld));
        when(clientRepository.findByEmail(clientNew.getEmail())).thenReturn(Optional.of(foundClient));
        try {
            clientService.updateClient(id, clientNew);
        } catch (EmailNotUniqueException e) {
            verify(clientRepository, never()).findByPhoneNumber(clientNew.getPhoneNumber());
            verify(clientRepository, never()).save(clientNew);
        }
    }

    @Test
    void updateClientPhoneNotUnique() {
        Long id = 1L;
        Client clientOld = Client.builder()
                .firstName("Client1 first name")
                .lastName("Client1 last name")
                .email("client1@gmail.com")
                .phoneNumber("0718180021")
                .orders(Arrays.asList(Order.builder().id(1L).build()))
                .build();
        Client clientNew = Client.builder()
                .firstName("Client2 first name")
                .lastName("Client2 last name")
                .email("client1@gmail.com")
                .phoneNumber("0728180022")
                .orders(null)
                .build();
        Client foundClient = Client.builder()
                .firstName("Client3 first name")
                .lastName("Client3 last name")
                .email("client2@gmail.com")
                .phoneNumber("0728180022")
                .orders(null)
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(clientOld));
        when(clientRepository.findByPhoneNumber(clientNew.getPhoneNumber()))
                .thenReturn(Optional.of(foundClient));
        try {
            clientService.updateClient(id, clientNew);
        } catch (PhoneNotUniqueException e) {
            verify(clientRepository, never()).save(clientNew);
        }
    }

    @Test
    void getOrdersOfClientHappyFlow() {
        // year not null, month not null
        Long id = 1L;
        Integer year = 2022;
        Integer month = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date1 = formatter.parse("2022-01-23");
            date2 = formatter.parse("2021-01-02");
            date3 = formatter.parse("2022-02-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Order order1 = Order.builder()
                .id(1L)
                .event(Event.builder().build())
                .orderDate(date1)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .event(Event.builder().build())
                .orderDate(date2)
                .build();

        Order order3 = Order.builder()
                .id(3L)
                .event(Event.builder().build())
                .orderDate(date3)
                .build();

        Client client = Client.builder()
                .id(id)
                .firstName("Client first name")
                .lastName("Client last name")
                .email("client@gmail.com")
                .phoneNumber("0728180020")
                .orders(Arrays.asList(order1, order2, order3))
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientRepository.getAllOrdersByYearAndMonth(id, year, month))
                .thenReturn(Arrays.asList(order1));

        List<Order> result = clientService.getOrdersOfClientByYearOrMonth(id, year, month);
        assertEquals(1, result.size());
        assertEquals(order1.getId(), result.get(0).getId());
    }

    @Test
    void getOrdersOfClientNotFound() {
        // year not null, month not null
        Long id = 1L;
        Integer year = 2022;
        Integer month = 1;
        Client client = Client.builder()
                .id(id)
                .firstName("Client first name")
                .lastName("Client last name")
                .email("client@gmail.com")
                .phoneNumber("0728180020")
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.empty());
        try {
            clientService.getOrdersOfClientByYearOrMonth(id, year, month);
        } catch (ClientNotFoundException e) {
            verify(clientRepository, never()).getAllOrdersByYearAndMonth(id, year, month);
        }
    }

    @Test
    void getOrdersOfClientYearNullMonthNull() {
        Long id = 1L;
        Integer year = null;
        Integer month = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date1 = formatter.parse("2022-01-23");
            date2 = formatter.parse("2021-01-02");
            date3 = formatter.parse("2022-02-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Order order1 = Order.builder()
                .id(1L)
                .event(Event.builder().build())
                .orderDate(date1)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .event(Event.builder().build())
                .orderDate(date2)
                .build();

        Order order3 = Order.builder()
                .id(3L)
                .event(Event.builder().build())
                .orderDate(date3)
                .build();

        Client client = Client.builder()
                .id(id)
                .firstName("Client first name")
                .lastName("Client last name")
                .email("client@gmail.com")
                .phoneNumber("0728180020")
                .orders(Arrays.asList(order1, order2, order3))
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        List<Order> result = clientService.getOrdersOfClientByYearOrMonth(id, year, month);
        assertEquals(3, result.size());
        assertEquals(order1.getId(), result.get(0).getId());
        assertEquals(order2.getId(), result.get(1).getId());
        assertEquals(order3.getId(), result.get(2).getId());
    }

    @Test
    void getOrdersOfClientMonthNull() {
        // year not null
        Long id = 1L;
        Integer year = 2022;
        Integer month = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date1 = formatter.parse("2022-01-23");
            date2 = formatter.parse("2021-01-02");
            date3 = formatter.parse("2022-02-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Order order1 = Order.builder()
                .id(1L)
                .event(Event.builder().build())
                .orderDate(date1)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .event(Event.builder().build())
                .orderDate(date2)
                .build();

        Order order3 = Order.builder()
                .id(3L)
                .event(Event.builder().build())
                .orderDate(date3)
                .build();

        Client client = Client.builder()
                .id(id)
                .firstName("Client first name")
                .lastName("Client last name")
                .email("client@gmail.com")
                .phoneNumber("0728180020")
                .orders(Arrays.asList(order1, order2, order3))
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientRepository.getAllOrdersByYear(id, year))
                .thenReturn(Arrays.asList(order1, order3));
        List<Order> result = clientService.getOrdersOfClientByYearOrMonth(id, year, month);
        assertEquals(2, result.size());
        assertEquals(order1.getId(), result.get(0).getId());
        assertEquals(order3.getId(), result.get(1).getId());
    }

    @Test
    void getOrdersOfClientYearNull() {
        // month not null
        Long id = 1L;
        Integer year = null;
        Integer month = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date1 = formatter.parse("2022-01-23");
            date2 = formatter.parse("2021-01-02");
            date3 = formatter.parse("2022-02-14");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Order order1 = Order.builder()
                .id(1L)
                .event(Event.builder().build())
                .orderDate(date1)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .event(Event.builder().build())
                .orderDate(date2)
                .build();

        Order order3 = Order.builder()
                .id(3L)
                .event(Event.builder().build())
                .orderDate(date3)
                .build();

        Client client = Client.builder()
                .id(id)
                .firstName("Client first name")
                .lastName("Client last name")
                .email("client@gmail.com")
                .phoneNumber("0728180020")
                .orders(Arrays.asList(order1, order2, order3))
                .build();
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientRepository.getAllOrdersByMonth(id, month))
                .thenReturn(Arrays.asList(order1, order2));
        List<Order> result = clientService.getOrdersOfClientByYearOrMonth(id, year, month);
        assertEquals(2, result.size());
        assertEquals(order1.getId(), result.get(0).getId());
        assertEquals(order2.getId(), result.get(1).getId());
    }
}
