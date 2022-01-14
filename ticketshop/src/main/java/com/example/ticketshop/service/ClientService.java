package com.example.ticketshop.service;

import com.example.ticketshop.exception.ClientNotFoundException;
import com.example.ticketshop.exception.EmailNotUniqueException;
import com.example.ticketshop.exception.PhoneNotUniqueException;
import com.example.ticketshop.model.Client;
import com.example.ticketshop.model.Order;
import com.example.ticketshop.repository.ClientRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClient(Long id) {

        Optional<Client> returnedClient = clientRepository.findById(id);
        if (returnedClient.isPresent())
            return returnedClient.get();
        else throw new ClientNotFoundException(id);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public List<Order> getOrdersOfClient(Long id) {
        Client client = getClient(id);
        return client.getOrders();
    }

    public List<Order> getOrdersOfClientByYearOrMonth(Long id, Integer year, Integer month) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isEmpty())
            throw new ClientNotFoundException(id);
        if (year == null && month == null) {
            return getOrdersOfClient(id);
        } else if (month == null) {
            return clientRepository.getAllOrdersByYear(id, year);
        } else if (year == null) {
            return clientRepository.getAllOrdersByMonth(id, month);
        }
        return clientRepository.getAllOrdersByYearAndMonth(id, year, month);
    }

    public Client updateClient(Long id, Client client) {
        Optional<Client> returnedClient = clientRepository.findById(id);
        if(returnedClient.isEmpty())
            throw new ClientNotFoundException(id);
        if(!returnedClient.get().getEmail().equals(client.getEmail())) // the email will be updated
            checkUniqueEmail(client.getEmail());
        if(!returnedClient.get().getPhoneNumber().equals(client.getPhoneNumber())) // the phone number will be updated
            checkUniquePhoneNumber(client.getPhoneNumber());

        Client newClient = returnedClient.get();
        newClient.setFirstName(client.getFirstName());
        newClient.setLastName(client.getLastName());
        newClient.setEmail(client.getEmail());
        newClient.setPhoneNumber(client.getPhoneNumber());
        return clientRepository.save(newClient);
    }

    private void checkUniqueEmail(String email) {
        Optional<Client> client = clientRepository.findByEmail(email);
        if(client.isPresent())
            throw new EmailNotUniqueException(email);
    }

    private void checkUniquePhoneNumber(String phone) {
        Optional<Client> client = clientRepository.findByPhoneNumber(phone);
        if(client.isPresent())
            throw new PhoneNotUniqueException(phone);
    }
}
