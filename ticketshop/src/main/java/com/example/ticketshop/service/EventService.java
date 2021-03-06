package com.example.ticketshop.service;

import com.example.ticketshop.exception.EventNotFoundException;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VenueService venueService;
    public EventService(EventRepository eventRepository, VenueService venueService) {
        this.eventRepository = eventRepository;
        this.venueService = venueService;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent())
            return event.get();
        else throw new EventNotFoundException(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> getAllEventsByMonthAndYear(Integer month,
                                                    Integer year) {
        if(month != null) {
            if(year != null)
                return eventRepository.getAllEventsByMonthAndYear(month, year);
            else return eventRepository.getAllEventsByMonth(month);
        } else {
            if(year != null)
                return eventRepository.getAllEventsByYear(year);
            else return eventRepository.findAll();
        }
    }

    public List<Event> getTopEventsBySoldTickets(Integer lim) {
        List<Event> events = eventRepository.getTopEventBySoldTickets();
        return eventRepository.getTopEventBySoldTickets().stream().limit(lim).collect(Collectors.toList());
    }

    public Integer getNumberOfAvailableSeats(Long eventId) {
        Optional<Event> optEvent = eventRepository.findById(eventId);
        if(optEvent.isEmpty())
            throw new EventNotFoundException(eventId);
        Event event = optEvent.get();
        Integer numSoldSeats = eventRepository.getNumberOfSoldSeats(eventId);
        if(numSoldSeats == null)
            numSoldSeats = 0;
        return event.getVenue().getSeatCapacity() - numSoldSeats;
    }

    public Event updateEvent(Long id, Event eventRequest) {
        Optional<Event> returnedEvent = eventRepository.findById(id);
        if(returnedEvent.isEmpty())
            throw new EventNotFoundException(id);

        Event newEvent = returnedEvent.get();
        newEvent.setVenue(eventRequest.getVenue());
        newEvent.setPrice(eventRequest.getPrice());
        newEvent.setDate(eventRequest.getDate());
        newEvent.setHour(eventRequest.getHour());
        return eventRepository.save(newEvent);
    }
}
