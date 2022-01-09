package com.example.ticketshop.mapper;

import com.example.ticketshop.dto.EventRequest;
import com.example.ticketshop.dto.EventResponse;
import com.example.ticketshop.model.Event;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.model.Venue;
import com.example.ticketshop.service.PlayService;
import com.example.ticketshop.service.VenueService;
import org.springframework.stereotype.Component;


@Component
public class EventMapper {

    private final PlayService playService;
    private final VenueService venueService;
    private final PlayMapper playMapper;
    private final VenueMapper venueMapper;

    public EventMapper(PlayService playService, VenueService venueService, PlayMapper playMapper, VenueMapper venueMapper) {
        this.playService = playService;
        this.venueService = venueService;
        this.playMapper = playMapper;
        this.venueMapper = venueMapper;
    }

    public Event toEntity(EventRequest eventRequest) {
        Play returnedPlay = null;
        if(eventRequest.getPlayId() != null)
        returnedPlay = playService.getPlay(eventRequest.getPlayId());

        Venue returnedVenue = null;
        if(eventRequest.getVenueId() != null)
            returnedVenue = venueService.getVenue(eventRequest.getVenueId());

        return Event.builder()
                .play(returnedPlay)
                .venue(returnedVenue)
                .price(eventRequest.getPrice())
                .date(eventRequest.getDate())
                .hour(eventRequest.getHour())
                .build();
    }

    public EventResponse toDtoResponse(Event event) {

        return EventResponse.builder()
                .id(event.getId())
                .play(playMapper.toDtoResponse(event.getPlay()))
                .venue(venueMapper.toDtoResponse(event.getVenue()))
                .price(event.getPrice())
                .date(event.getDate())
                .hour(event.getHour())
                .build();
    }
}
