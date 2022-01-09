package com.example.ticketshop.dto;

import com.example.ticketshop.model.Play;
import com.example.ticketshop.model.Venue;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EventResponse {
    private Long id;

    private PlayResponse play;

    private VenueResponse venue;

    private Double price;

    private Date date;

    private String hour;

}
