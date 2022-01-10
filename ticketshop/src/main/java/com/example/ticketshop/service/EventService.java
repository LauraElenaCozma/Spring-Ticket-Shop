package com.example.ticketshop.service;

import com.example.ticketshop.exception.EventNotFoundException;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.repository.EventRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
        return eventRepository.getAllEventsByMonthAndYear(month, year);
    }

    public List<Event> getTopEventsBySoldTickets(Integer lim) {
        List<Event> events = eventRepository.getTopEventBySoldTickets();
        return eventRepository.getTopEventBySoldTickets().stream().limit(lim).collect(Collectors.toList());
    }

    public Integer getNumberOfAvailableSeats(Long eventId) {
        Optional<Event> optEvent = eventRepository.findById(eventId);
        Event event = null;
        if(optEvent.isPresent())
            event = optEvent.get();
        else throw new EventNotFoundException(eventId);
        return event.getVenue().getSeatCapacity() - eventRepository.getNumberOfSoldSeats(eventId);
    }
}
