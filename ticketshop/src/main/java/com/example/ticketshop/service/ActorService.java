package com.example.ticketshop.service;

import com.example.ticketshop.exception.ActorNotFoundException;
import com.example.ticketshop.model.Actor;
import com.example.ticketshop.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor getActor(Long id) {
        Optional<Actor> returnedActor = actorRepository.findById(id);
        if (returnedActor.isPresent()) {
            return returnedActor.get();
        } else {
            throw new ActorNotFoundException(id);
        }
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }

    public List<Actor> getActiveActorsByYear(Integer year) {
        return actorRepository.findAll().stream()
                .filter(actor -> actor.getPlays().stream()
                        .filter(play -> play.getEvents().stream()
                                .filter(event -> compareYears(event.getDate(), year))
                                .count() >= 1)
                        .count() >= 1)
                .collect(Collectors.toList());
    }

    private Boolean compareYears(Date date, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.YEAR)) == year;
    }
}
