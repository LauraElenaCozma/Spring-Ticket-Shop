package com.example.ticketshop.service;

import com.example.ticketshop.exception.VenueNotFoundException;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }


    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue getVenue(Long id) {
        Optional<Venue> returnedVenue = venueRepository.findById(id);
        if(returnedVenue.isPresent())
            return returnedVenue.get();
        else throw new VenueNotFoundException(id);
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }


    public Venue updateVenue(Long id, Venue venueRequest) {
        Optional<Venue> venue = venueRepository.findById(id);
        if(venue.isEmpty())
            throw new VenueNotFoundException(id);
        Venue newVenue = venue.get();
        newVenue.setVenueName(venueRequest.getVenueName());
        newVenue.setLocationName(venueRequest.getLocationName());
        newVenue.setSeatCapacity(venueRequest.getSeatCapacity());
        return venueRepository.save(newVenue);
    }
}
