package com.example.ticketshop.controller;

import com.example.ticketshop.dto.ActorRequest;
import com.example.ticketshop.dto.ActorResponse;
import com.example.ticketshop.mapper.ActorMapper;
import com.example.ticketshop.model.Actor;
import com.example.ticketshop.service.ActorService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/actors")
@Validated
public class ActorController {

    public final ActorService actorService;
    public final ActorMapper actorMapper;


    public ActorController(ActorService actorService, ActorMapper actorMapper) {
        this.actorService = actorService;
        this.actorMapper = actorMapper;
    }

    @PostMapping
    public ResponseEntity<ActorResponse> createActor(
            @Valid
            @RequestBody ActorRequest actorRequest) {
        Actor actor = actorMapper.toEntity(actorRequest);
        Actor savedActor = actorService.createActor(actor);
        return ResponseEntity.created(URI.create("/actors/" + savedActor.getId()))
                .body(actorMapper.toDtoResponse(savedActor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActorResponse> getActor(@PathVariable Long id) {
        Actor returnedActor = actorService.getActor(id);
        return ResponseEntity.ok().body(actorMapper.toDtoResponse(returnedActor));

    }

    @GetMapping
    public ResponseEntity<List<ActorResponse>> getAllActors() {
        return ResponseEntity.ok()
                .body(actorService.getAllActors().stream()
                        .map(actorMapper::toDtoResponse).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public void deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
    }

    @GetMapping("/activeActors")
    public ResponseEntity<List<ActorResponse>> getActiveActorsByYear(@RequestParam Integer year) {
        return ResponseEntity.ok()
                .body(actorService.getActiveActorsByYear(year).stream()
                        .map(actorMapper::toDtoResponse)
                        .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActorResponse> updateActor(@PathVariable Long id,
                                                     @Valid
                                                     @RequestBody ActorRequest actorRequest) {
        Actor actor = actorService.updateActor(id, actorMapper.toEntity(actorRequest));
        return ResponseEntity.ok().body(actorMapper.toDtoResponse(actor));
    }
}
