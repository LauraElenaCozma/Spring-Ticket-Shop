package com.example.ticketshop.service;

import com.example.ticketshop.exception.PlayNotFoundException;
import com.example.ticketshop.model.Genre;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.repository.PlayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayServiceTest {
    @InjectMocks
    private PlayService playService;
    @Mock
    private PlayRepository playRepository;

    @Test
    void createPlayHappyFlow() {
        Play play = Play.builder()
                .name("Name")
                .author("Author")
                .director("Director")
                .duration(100D)
                .genre(Genre.TRAGEDY)
                .build();
        when(playRepository.save(play)).thenReturn(play);
        Play result = playService.createPlay(play);

        assertNotNull(result);
        assertEquals(play.getName(), result.getName());
        assertEquals(play.getAuthor(), result.getAuthor());
    }

    @Test
    void updatePlayHappyFlow() {
        Long id = 1L;
        Play newPlay = Play.builder()
                .id(id)
                .name("Play1")
                .genre(Genre.COMEDY)
                .build();
        Play oldPlay = Play.builder()
                .id(id)
                .name("Play2")
                .genre(Genre.DRAMA)
                .build();
        when(playRepository.findById(id)).thenReturn(Optional.of(oldPlay));
        when(playRepository.save(newPlay)).thenReturn(newPlay);
        Play result = playService.updatePlay(id, newPlay);
        assertEquals(newPlay.getName(), result.getName());
    }

    @Test
    void updatePlayNegativeFlow() {
        Long id = 1L;
        Play newPlay = Play.builder()
                .id(id)
                .name("Play1")
                .genre(Genre.COMEDY)
                .build();
        when(playRepository.findById(id)).thenReturn(Optional.empty());
        try {
            playService.updatePlay(id, newPlay);
        } catch (PlayNotFoundException e) {
            assertEquals("Play with id " + id + " was not found", e.getMessage());
            verify(playRepository, never()).save(newPlay);
        }
    }

    @Test
    void getPlaysHappyFlow() {
        Play play1 = Play.builder()
                .name("Name 1")
                .author("Author 1")
                .director("Director 1")
                .duration(101D)
                .genre(Genre.TRAGEDY)
                .build();
        Play play2 = Play.builder()
                .name("Name 2")
                .author("Author 2")
                .director("Director 2")
                .duration(102D)
                .genre(Genre.DRAMA)
                .build();
        when(playRepository.findAll()).thenReturn(Arrays.asList(play1, play2));
        List<Play> plays = playService.getPlays();
        assertEquals(play1.getName(), plays.get(0).getName());
        assertEquals(play1.getDirector(), plays.get(0).getDirector());
        assertEquals(play2.getName(), plays.get(1).getName());
        assertEquals(play2.getDirector(), plays.get(1).getDirector());
    }

    @Test
    void getPlayHappyFlow() {
        Long id = 1L;
        Play play = Play.builder()
                .id(id)
                .name("Name")
                .author("Author")
                .director("Director")
                .duration(100D)
                .genre(Genre.TRAGEDY)
                .build();

        when(playRepository.findById(id)).thenReturn(Optional.of(play));
        Play result = playService.getPlay(id);

        assertNotNull(result);
        assertEquals(play.getId(), result.getId());
        assertEquals(play.getName(), result.getName());
    }

    @Test
    void getVenueNegativeFlow() {
        Long id = 1L;
        String expected = "Play with id " + id + " was not found";

        PlayNotFoundException result = assertThrows(
                PlayNotFoundException.class,
                () -> playService.getPlay(id));

        assertEquals(expected, result.getMessage());
    }

    @Test
    void deletePlayHappyFlow() {
        Long id = 1L;
        doNothing().when(playRepository).deleteById(id);
        playService.deletePlay(id);
        verify(playRepository, times(1)).deleteById(id);
    }
}
