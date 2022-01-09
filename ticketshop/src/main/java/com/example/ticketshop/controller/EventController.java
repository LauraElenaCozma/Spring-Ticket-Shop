package com.example.ticketshop.controller;

import com.example.ticketshop.dto.EventRequest;
import com.example.ticketshop.dto.EventResponse;
import com.example.ticketshop.mapper.EventMapper;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.service.EventService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@Validated
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @Valid
            @RequestBody EventRequest eventRequest) {
        Event event = eventMapper.toEntity(eventRequest);
        Event savedEvent = eventService.createEvent(event);
        return ResponseEntity.created(URI.create("/events/" + savedEvent.getId()))
                .body(eventMapper.toDtoResponse(savedEvent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        return ResponseEntity.ok().body(eventMapper.toDtoResponse(event));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents().stream()
                .map(eventMapper::toDtoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(events);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<EventResponse>> getAllEventsByMonthAndYear(@RequestParam Integer month,
                                                                          @RequestParam Integer year) {
        return ResponseEntity.ok()
                .body(eventService.getAllEventsByMonthAndYear(month, year).stream()
                        .map(eventMapper::toDtoResponse)
                        .collect(Collectors.toList()));
    }

    @PostMapping("/topEvents")
    public ResponseEntity<List<EventResponse>> getTop(@RequestParam Integer limit) {
        return ResponseEntity.ok()
                .body(eventService.getTopEventBySoldTickets(limit).stream()
                        .map(eventMapper::toDtoResponse)
                        .collect(Collectors.toList()));
    }

    @GetMapping("{id}/availableSeats")
    public ResponseEntity<Integer> getNumberOfAvailableSeats(@PathVariable Long id) {
        Integer numAvailable = eventService.getNumberOfAvailableSeats(id);
        return ResponseEntity.ok().body(numAvailable);
    }
}
