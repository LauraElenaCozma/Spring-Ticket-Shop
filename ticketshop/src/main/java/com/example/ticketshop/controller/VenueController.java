package com.example.ticketshop.controller;

import com.example.ticketshop.dto.VenueRequest;
import com.example.ticketshop.dto.VenueResponse;
import com.example.ticketshop.mapper.VenueMapper;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/venues")
@Validated
public class VenueController {

    private final VenueService venueService;
    private final VenueMapper venueMapper;

    public VenueController(VenueService venueService, VenueMapper venueMapper) {
        this.venueService = venueService;
        this.venueMapper = venueMapper;
    }

    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(
            @Valid
            @RequestBody VenueRequest venueRequest) {
        Venue venue = venueMapper.toEntity(venueRequest);
        Venue savedVenue = venueService.createVenue(venue);
        VenueResponse venueResponse = venueMapper.toDtoResponse(savedVenue);
        return ResponseEntity.created(URI.create("/venues/" + savedVenue.getId()))
                             .body(venueResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenue(@PathVariable Long id) {
        Venue returnedVenue = venueService.getVenue(id);
        return ResponseEntity.ok().body(venueMapper.toDtoResponse(returnedVenue));
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues() {
        List<VenueResponse> venuesResponse = venueService.getAllVenues().stream()
                .map(venueMapper::toDtoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(venuesResponse);
    }

    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> updateVenue(@PathVariable Long id,
                                                     @Valid
                            @RequestBody VenueRequest venueRequest) {
        Venue venue = venueService.updateVenue(id, venueMapper.toEntity(venueRequest));
        return ResponseEntity.ok().body(venueMapper.toDtoResponse(venue));
    }
}
