package com.example.ticketshop.mapper;

import com.example.ticketshop.dto.VenueRequest;
import com.example.ticketshop.dto.VenueResponse;
import com.example.ticketshop.model.Venue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VenueMapper {
    public Venue toEntity(VenueRequest venueRequest) {
        return Venue.builder()
                .venueName(venueRequest.getVenueName())
                .locationName(venueRequest.getLocationName())
                .seatCapacity(venueRequest.getSeatCapacity())
                .build();
    }

    public VenueResponse toDtoResponse(Venue venue) {
        List<Long> eventIds = new ArrayList<>();
        if (venue.getEvents() != null) {
            eventIds = venue.getEvents()
                    .stream()
                    .map(event -> event.getId())
                    .collect(Collectors.toList());
        }

        return VenueResponse.builder()
                .id(venue.getId())
                .venueName(venue.getVenueName())
                .locationName(venue.getLocationName())
                .seatCapacity(venue.getSeatCapacity())
                .eventIds(eventIds)
                .build();
    }
}
