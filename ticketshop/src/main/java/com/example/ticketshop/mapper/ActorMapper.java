package com.example.ticketshop.mapper;

import com.example.ticketshop.dto.ActorRequest;
import com.example.ticketshop.dto.ActorResponse;
import com.example.ticketshop.model.Actor;
import org.springframework.stereotype.Component;

@Component
public class ActorMapper {
    public Actor toEntity(ActorRequest actorRequest) {
        return Actor.builder()
                .firstName(actorRequest.getFirstName())
                .lastName(actorRequest.getLastName())
                .dateOfBirth(actorRequest.getDateOfBirth())
                .placeOfBirth(actorRequest.getPlaceOfBirth())
                .build();
    }

    public ActorResponse toDtoResponse(Actor actor) {
        return ActorResponse.builder()
                .id(actor.getId())
                .firstName(actor.getFirstName())
                .lastName(actor.getLastName())
                .dateOfBirth(actor.getDateOfBirth())
                .placeOfBirth(actor.getPlaceOfBirth())
                .build();
    }
}
