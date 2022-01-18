package com.example.ticketshop.service;

import com.example.ticketshop.exception.EventNotFoundException;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;
    @Mock
    private EventRepository eventRepository;

    @Test
    void createEventHappyFlow() {
        Event event = Event.builder()
                .price(100D)
                .hour("20:30")
                .build();
        when(eventRepository.save(event)).thenReturn(event);
        Event result = eventService.createEvent(event);
        assertNotNull(result);
        assertEquals(event.getPrice(), result.getPrice());
    }

    @Test
    void getAllEventsHappyFlow() {
        Event event1 = Event.builder()
                .id(1L)
                .price(101D)
                .hour("20:30")
                .build();
        Event event2 = Event.builder()
                .id(2L)
                .price(102D)
                .hour("20:40")
                .build();
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));
        List<Event> events = eventService.getAllEventsByMonthAndYear(null, null);
        assertEquals(event1.getPrice(), events.get(0).getPrice());
        assertEquals(event2.getPrice(), events.get(1).getPrice());
    }

    @Test
    void getEventHappyFlow() {
        Long id = 1L;
        Event event = Event.builder()
                .id(id)
                .price(100D)
                .hour("20:30")
                .build();
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        Event result = eventService.getEvent(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(event.getPrice(), result.getPrice());
    }

    @Test
    void getEventNegativeFlow() {
        Long id = 1L;
        String expected = "Event with id " + id + " was not found";
        EventNotFoundException result = assertThrows(EventNotFoundException.class,
                () -> eventService.getEvent(id));
        assertEquals(expected, result.getMessage());
    }

    @Test
    void deleteEventHappyFlow() {
        Long id = 1L;
        doNothing().when(eventRepository).deleteById(id);
        eventService.deleteEvent(id);
        verify((eventRepository), times(1)).deleteById(id);
    }

    @Test
    void getAllEventsByMonthAndYearHappyFlow() {
        Integer month = 1;
        Integer year = 2022;
        Event event1 = Event.builder()
                .id(1L)
                .price(101D)
                .hour("20:30")
                .build();
        Event event2 = Event.builder()
                .id(2L)
                .price(102D)
                .hour("20:40")
                .build();
        when(eventRepository.getAllEventsByMonthAndYear(month, year))
                .thenReturn(Arrays.asList(event1, event2));
        List<Event> events = eventService.getAllEventsByMonthAndYear(month, year);
        assertEquals(event1.getId(), events.get(0).getId());
        assertEquals(event2.getId(), events.get(1).getId());
    }

    @Test
    void getTopEventsBySoldTicketsHappyFlow() {
        Integer lim = 1;
        Event event1 = Event.builder()
                .id(1L)
                .price(101D)
                .hour("20:30")
                .build();
        Event event2 = Event.builder()
                .id(2L)
                .price(102D)
                .hour("20:40")
                .build();
        when(eventRepository.getTopEventBySoldTickets())
                .thenReturn(Arrays.asList(event1, event2));
        List<Event> events = eventService.getTopEventsBySoldTickets(lim);
        assertEquals(1, events.size());
        assertEquals(event1.getId(), events.get(0).getId());
    }

    @Test
    void getNumberOfAvailableSeatsHappyFlow() {
        Long eventId = 1L;
        Integer capacity = 100;
        Integer sold = 10;
        Integer expectedAvailable = capacity - sold;
        Event event = Event.builder()
                .id(eventId)
                .price(100D)
                .hour("20:30")
                .venue(Venue.builder()
                        .seatCapacity(capacity)
                        .build())
                .build();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.getNumberOfSoldSeats(eventId)).thenReturn(sold);
        Integer result = eventService.getNumberOfAvailableSeats(eventId);
        assertEquals(expectedAvailable, result);
    }

    @Test
    void getNumberOfAvailableSeatsReturnsNull() {
        Long eventId = 1L;
        Integer capacity = 100;
        Event event = Event.builder()
                .id(eventId)
                .price(100D)
                .hour("20:30")
                .venue(Venue.builder()
                        .seatCapacity(capacity)
                        .build())
                .build();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.getNumberOfSoldSeats(eventId)).thenReturn(null);
        Integer result = eventService.getNumberOfAvailableSeats(eventId);
        assertEquals(capacity, result);
    }
    @Test
    void getNumberOfAvailableSeatsNegativeFlow() {
        Long eventId = 1L;
        String expected = "Event with id " + eventId + " was not found";
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        try {
            eventService.getNumberOfAvailableSeats(eventId);
        } catch (EventNotFoundException e) {
            verify(eventRepository, times(0)).getNumberOfSoldSeats(eventId);
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    void updateEventNotFound() {
        Long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());
        try {
            eventService.updateEvent(id, Event.builder().build());
        } catch (EventNotFoundException e) {
            assertEquals("Event with id " + id + " was not found", e.getMessage());
            verify(eventRepository, never()).save(Event.builder().build());
        }
    }

    @Test
    void updateEventHappyFlow() {
        Long id = 1L;
        Event newEvent = Event.builder()
                .id(1L)
                .price(101D)
                .hour("20:30")
                .play(null)
                .build();
        Event oldEvent = Event.builder()
                .id(1L)
                .price(102D)
                .hour("20:40")
                .play(Play.builder().name("Play").build())
                .build();
        Event savedEvent = Event.builder()
                .id(1L)
                .price(101D)
                .hour("20:30")
                .play(Play.builder().name("Play").build())
                .build();
        when(eventRepository.findById(id)).thenReturn(Optional.of(oldEvent));
        when(eventRepository.save(savedEvent)).thenReturn(savedEvent);
        Event result = eventService.updateEvent(id, newEvent);
        assertEquals(savedEvent.getPrice(), result.getPrice());
        assertNotNull(result.getPlay());
    }
}
