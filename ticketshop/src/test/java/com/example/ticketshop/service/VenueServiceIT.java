package com.example.ticketshop.service;

import com.example.ticketshop.model.Venue;
import com.example.ticketshop.repository.VenueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class VenueServiceIT {
    @MockBean
    private VenueRepository venueRepository;
    @Autowired
    private VenueService venueService;

    @Test
    public void createVenueHappyFlow() {
        Venue venue = Venue.builder()
                .venueName("Venue")
                .locationName("Location")
                .seatCapacity(100)
                .build();
        Venue savedVenue = Venue.builder()
                .id(1L)
                .venueName("Venue")
                .locationName("Location")
                .seatCapacity(100)
                .build();
        when(venueRepository.save(venue)).thenReturn(savedVenue);
        Venue result = venueService.createVenue(venue);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(venue.getVenueName(), result.getVenueName());
    }

}
