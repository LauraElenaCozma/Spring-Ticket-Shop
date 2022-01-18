package com.example.ticketshop.controller;

import com.example.ticketshop.dto.EventRequest;
import com.example.ticketshop.dto.EventRequestUpdate;
import com.example.ticketshop.dto.EventResponse;
import com.example.ticketshop.mapper.EventMapper;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.service.EventService;
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
        Event event = eventMapper.eventRequestToEntity(eventRequest);
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
    public ResponseEntity<List<EventResponse>> getAllEventsByMonthAndYear(@RequestParam(required = false) Integer month,
                                                                          @RequestParam(required = false) Integer year) {
        return ResponseEntity.ok()
                .body(eventService.getAllEventsByMonthAndYear(month, year).stream()
                        .map(eventMapper::toDtoResponse)
                        .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }


    @GetMapping("/topEvents")
    public ResponseEntity<List<EventResponse>> getTop(@RequestParam Integer limit) {
        return ResponseEntity.ok()
                .body(eventService.getTopEventsBySoldTickets(limit).stream()
                        .map(eventMapper::toDtoResponse)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}/availableSeats")
    public ResponseEntity<Integer> getNumberOfAvailableSeats(@PathVariable Long id) {
        Integer numAvailable = eventService.getNumberOfAvailableSeats(id);
        return ResponseEntity.ok().body(numAvailable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id,
                                                     @Valid
                                                     @RequestBody EventRequestUpdate eventRequestUpdate) {
        Event event = eventService.updateEvent(id, eventMapper.eventRequestUpdateToEntity(eventRequestUpdate));
        return ResponseEntity.ok().body(eventMapper.toDtoResponse(event));
    }
}
