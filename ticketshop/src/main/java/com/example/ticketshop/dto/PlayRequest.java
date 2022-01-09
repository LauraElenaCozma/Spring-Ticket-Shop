package com.example.ticketshop.dto;

import com.example.ticketshop.model.Genre;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

@Data
@Builder
public class PlayRequest {
    @NotEmpty(message = "The name must not be empty!")
    private String name;

    private String author;

    private String director;

    private Genre genre;

    @NotNull(message = "The duration must not be null!")
    @Min(value = 0, message = "The duration must be a positive value!")
    private Double duration;

    @NotEmpty(message = "The actors must not be an empty array!")
    private List<Long> actorIds;
}
