package com.example.ticketshop.controller;

import com.example.ticketshop.dto.PlayRequest;
import com.example.ticketshop.dto.PlayResponse;
import com.example.ticketshop.mapper.PlayMapper;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.service.PlayService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plays")
@Validated
public class PlayController {
    private final PlayService playService;
    private final PlayMapper playMapper;

    public PlayController(PlayService playService, PlayMapper playMapper) {
        this.playService = playService;
        this.playMapper = playMapper;
    }

    @PostMapping
    public ResponseEntity<PlayResponse> createPlay(
            @Valid
            @RequestBody PlayRequest playRequest) {
        Play savedPlay = playService.createPlay(playMapper.toEntity(playRequest));
        return ResponseEntity.created(URI.create("/plays/" + savedPlay.getId()))
                .body(playMapper.toDtoResponse(savedPlay));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayResponse> getPlay(@PathVariable Long id) {
        Play play = playService.getPlay(id);
        return ResponseEntity.ok().body(playMapper.toDtoResponse(play));
    }

    @GetMapping
    public ResponseEntity<List<PlayResponse>> getPlays() {
        List<PlayResponse> plays = playService.getPlays().stream()
                .map(playMapper::toDtoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(plays);
    }

    @DeleteMapping("/{id}")
    public void deletePlay(@PathVariable Long id) {
        playService.deletePlay(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayResponse> updatePlay(@PathVariable Long id,
                                                   @Valid
                                                   @RequestBody PlayRequest playRequest) {
        Play play = playService.updatePlay(id, playMapper.toEntity(playRequest));
        return ResponseEntity.ok().body(playMapper.toDtoResponse(play));
    }
}