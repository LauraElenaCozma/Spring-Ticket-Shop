package com.example.ticketshop.service;

import com.example.ticketshop.exception.VenueNotFoundException;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VenueServiceTest {

    @InjectMocks
    private VenueService venueService;
    @Mock
    private VenueRepository venueRepository;

    @Test
    void createVenueHappyFlow() {
        Venue venue = Venue.builder()
                .venueName("Venue name")
                .locationName("Location name")
                .seatCapacity(100)
                .build();
        when(venueRepository.save(venue)).thenReturn(venue);
        Venue result = venueService.createVenue(venue);
        assertEquals(venue.getLocationName(), result.getLocationName());
        assertEquals(venue.getSeatCapacity(), result.getSeatCapacity());
    }

    @Test
    void getAllVenuesHappyFlow() {
        Venue venue1 = Venue.builder()
                .id(1L)
                .venueName("Venue1 name")
                .locationName("Location1 name")
                .seatCapacity(100)
                .build();
        Venue venue2 = Venue.builder()
                .id(2L)
                .venueName("Venue2 name")
                .locationName("Location2 name")
                .seatCapacity(102)
                .build();
        when(venueRepository.findAll()).thenReturn(Arrays.asList(venue1, venue2));

        List<Venue> result = venueService.getAllVenues();
        assertEquals(venue1.getVenueName(), result.get(0).getVenueName());
        assertEquals(venue2.getVenueName(), result.get(1).getVenueName());
    }

    @Test
    void getVenueHappyFlow() {
        Long id = 1L;
        Venue venue = Venue.builder()
                .id(1L)
                .venueName("Venue name")
                .locationName("Location name")
                .seatCapacity(100)
                .build();
        when(venueRepository.findById(id)).thenReturn(Optional.of(venue));
        Venue result = venueService.getVenue(id);
        assertNotNull(result);
        assertEquals(venue.getLocationName(), result.getLocationName());
        assertEquals(venue.getVenueName(), result.getVenueName());
    }

    @Test
    void getVenueNegativeFlow() {
        Long id = 1L;
        String expected = "Venue with id " + id + " was not found";

        VenueNotFoundException result = assertThrows(VenueNotFoundException.class,
                () -> venueService.getVenue(id));

        assertEquals(expected, result.getMessage());
    }

    @Test
    void deleteVenue() {
        Long id = 1L;
        doNothing().when(venueRepository).deleteById(id);
        venueService.deleteVenue(id);
        verify(venueRepository, times(1)).deleteById(id);
    }

    @Test
    void updateVenueHappyFlow() {
        Long id = 1L;
        Venue newVenue = Venue.builder()
                .id(id)
                .venueName("Venue1")
                .locationName("Location1")
                .seatCapacity(101)
                .events(null)
                .build();
        Venue oldVenue = Venue.builder()
                .id(id)
                .venueName("Venue2")
                .locationName("Location2")
                .seatCapacity(102)
                .events(Arrays.asList(Event.builder().id(100L).build()))
                .build();
        Venue savedVenue = Venue.builder()
                .id(id)
                .venueName("Venue1")
                .locationName("Location1")
                .seatCapacity(101)
                .events(Arrays.asList(Event.builder().id(100L).build()))
                .build();
        when(venueRepository.findById(id)).thenReturn(Optional.of(oldVenue));
        when(venueRepository.save(savedVenue)).thenReturn(savedVenue);
        Venue result = venueService.updateVenue(id, newVenue);
        assertEquals(savedVenue.getVenueName(), result.getVenueName());
        assertNotNull(result.getEvents());
    }

    @Test
    void updateVenueNegativeFlow() {
        Long id = 1L;
        when(venueRepository.findById(id)).thenReturn(Optional.empty());
        try {
            venueService.updateVenue(id, Venue.builder().build());
        } catch (VenueNotFoundException e) {
            assertEquals("Venue with id " + id + " was not found", e.getMessage());
        }

    }
}
