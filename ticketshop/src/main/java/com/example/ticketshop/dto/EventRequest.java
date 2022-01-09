package com.example.ticketshop.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class EventRequest {
    @NotNull(message = "The play must not be null")
    private Long playId;

    @NotNull(message = "The venue must not be null")
    private Long venueId;

    @NotNull(message = "The price must not be null")
    @Min(value = 0, message = "The price must have a positive value")
    private Double price;

    @NotNull(message = "The date must not be null")
    private Date date;

    @NotEmpty(message = "The hour must not be null")
    private String hour;
}
