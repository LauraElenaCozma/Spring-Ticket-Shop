package com.example.ticketshop.dto;

import com.example.ticketshop.model.Actor;
import com.example.ticketshop.model.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PlayResponse {
    private Long id;

    private String name;

    private String author;

    private String director;

    private Genre genre;

    private Double duration;

    private List<ActorResponse> actors;
}
