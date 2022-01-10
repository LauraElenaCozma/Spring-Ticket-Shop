package com.example.ticketshop.service;

import com.example.ticketshop.exception.ActorNotFoundException;
import com.example.ticketshop.model.Actor;
import com.example.ticketshop.repository.ActorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTest {

    @InjectMocks
    private ActorService actorService;
    @Mock
    private ActorRepository actorRepository;

    @Test
    void createActorHappyFlow() {
        Actor actor = Actor.builder()
                .firstName("First name")
                .lastName("Last name")
                .placeOfBirth("Bucharest")
                .build();
        when(actorRepository.save(actor)).thenReturn(actor);

        Actor result = actorService.createActor(actor);

        assertEquals(actor.getFirstName(), result.getFirstName());
        assertEquals(actor.getLastName(), result.getLastName());
    }

    @Test
    void getAllActorsHappyFlow() {
        Actor actor1 = Actor.builder()
                .id(1L)
                .firstName("First name 1")
                .lastName("Last name 1")
                .placeOfBirth("Bucharest")
                .build();
        Actor actor2 = Actor.builder()
                .id(2L)
                .firstName("First name 2")
                .lastName("Last name 2")
                .placeOfBirth("Prague")
                .build();
        when(actorRepository.findAll()).thenReturn(Arrays.asList(actor1, actor2));

        List<Actor> result = actorService.getAllActors();

        assertEquals(actor1.getFirstName(), result.get(0).getFirstName());
        assertEquals(actor1.getLastName(), result.get(0).getLastName());
        assertEquals(actor2.getFirstName(), result.get(1).getFirstName());
        assertEquals(actor2.getLastName(), result.get(1).getLastName());
    }

    @Test
    void getActorHappyFlow() {
        Long id = 1L;
        Actor actor = Actor.builder()
                .id(id)
                .firstName("First name")
                .lastName("Last name")
                .placeOfBirth("Bucharest")
                .build();
        when(actorRepository.findById(id)).thenReturn(Optional.of(actor));
        Actor result = actorService.getActor(id);
        assertNotNull(result);
        assertEquals(actor.getFirstName(), result.getFirstName());
    }

    @Test
    void getActorNegativeFlow() {
        Long id = 1L;
        String expected = "Actor with id " + id + " was not found";
        ActorNotFoundException result = assertThrows(ActorNotFoundException.class,
                () -> actorService.getActor(id));
        assertEquals(expected, result.getMessage());
    }

    @Test
    void deleteActorHappyFlow() {
        Long id = 1L;
        doNothing().when(actorRepository).deleteById(id);
        actorService.deleteActor(id);
        verify(actorRepository, times(1)).deleteById(id);
    }

}
