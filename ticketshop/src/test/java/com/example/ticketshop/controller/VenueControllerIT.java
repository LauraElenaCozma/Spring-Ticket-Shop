package com.example.ticketshop.controller;

import com.example.ticketshop.dto.VenueRequest;
import com.example.ticketshop.dto.VenueResponse;
import com.example.ticketshop.exception.VenueNotFoundException;
import com.example.ticketshop.mapper.VenueMapper;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.service.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = VenueController.class)
//@ComponentScan(basePackageClasses = {VenueMapper.class})
public class VenueControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper; //serializes our object
    @MockBean
    private VenueService venueService;
    @MockBean
    private VenueMapper venueMapper;
//    @MockBean
//    private PlayService playService;
//    @MockBean
//    private ActorService actorService;
//    @MockBean
//    private EventService eventService;
//    @MockBean
//    private ClientService clientService;

    @Test
    public void createVenueHappyFlow() throws Exception {
        VenueRequest request = VenueRequest.builder()
                .venueName("Sala Pictura")
                .locationName("TNB")
                .seatCapacity(200)
                .build();

        Venue venue = Venue.builder()
                .id(1L)
                .venueName("Sala Pictura")
                .locationName("TNB")
                .seatCapacity(200)
                .build();
        when(venueService.createVenue(any())).thenReturn(venue);
        when(venueMapper.toDtoResponse(venue)).thenReturn(VenueResponse.builder()
                .id(1L)
                .venueName("Sala Pictura")
                .locationName("TNB")
                .seatCapacity(200)
                .build());
        mockMvc.perform(post("/venues")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.locationName").value(request.getLocationName()))
                .andExpect(jsonPath("$.venueName").value(request.getVenueName()));
    }

    @Test
    public void getVenueHappyFlow() throws Exception {
        Long id = 1L;
        Venue venue = Venue.builder()
                .id(id)
                .venueName("Sala Pictura")
                .locationName("TNB")
                .seatCapacity(200)
                .build();
        when(venueService.getVenue(id)).thenReturn(venue);
        when(venueMapper.toDtoResponse(venue)).thenReturn(VenueResponse.builder()
                .id(id)
                .venueName("Sala Pictura")
                .locationName("TNB")
                .seatCapacity(200)
                .build());
        mockMvc.perform(get("/venues/{id}", id)
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.locationName").value(venue.getLocationName()))
                .andExpect(jsonPath("$.venueName").value(venue.getVenueName()));
    }

    @Test
    public void getVenueNegativeFlow() throws Exception {
        Long id = 1L;
        when(venueService.getVenue(id)).thenThrow(new VenueNotFoundException(id));
        mockMvc.perform(get("/venues/{id}", id)
        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}
