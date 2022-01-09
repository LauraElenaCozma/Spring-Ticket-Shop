package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class VenueResponse {
    private Long id;

    private String venueName;

    private String locationName;

    private Integer seatCapacity;

    private List<Long> eventIds;
}
