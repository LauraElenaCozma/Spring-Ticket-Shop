package com.example.ticketshop.mapper;

import com.example.ticketshop.dto.ActorResponse;
import com.example.ticketshop.dto.PlayRequest;
import com.example.ticketshop.dto.PlayResponse;
import com.example.ticketshop.model.Actor;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.service.ActorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayMapper {

    private final ActorService actorService;
    private final ActorMapper actorMapper;

    public PlayMapper(ActorService actorService, ActorMapper actorMapper) {
        this.actorService = actorService;
        this.actorMapper = actorMapper;
    }

    public Play toEntity(PlayRequest playRequest) {
        List<Actor> actors = new ArrayList<>();
        if(playRequest.getActorIds() != null) {
            for (Long id : playRequest.getActorIds()) {
                Actor returnedActor = actorService.getActor(id);
                actors.add(returnedActor);
            }
        }
        return Play.builder()
                .name(playRequest.getName())
                .author(playRequest.getAuthor())
                .director(playRequest.getDirector())
                .genre(playRequest.getGenre())
                .duration(playRequest.getDuration())
                .actors(actors)
                .build();
    }

    public PlayResponse toDtoResponse(Play play) {
        List<ActorResponse> actors = new ArrayList<>();
        if(play.getActors() != null)
            actors = play.getActors().stream().map(actorMapper::toDtoResponse).collect(Collectors.toList());
        return PlayResponse.builder()
                .id(play.getId())
                .name(play.getName())
                .author(play.getAuthor())
                .director(play.getDirector())
                .genre(play.getGenre())
                .duration(play.getDuration())
                .actors(actors)
                .build();
    }
}
