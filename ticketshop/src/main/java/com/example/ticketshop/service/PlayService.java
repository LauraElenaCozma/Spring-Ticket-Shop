package com.example.ticketshop.service;

import com.example.ticketshop.exception.PlayNotFoundException;
import com.example.ticketshop.model.Play;
import com.example.ticketshop.repository.PlayRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class PlayService {

    private final PlayRepository playRepository;

    public PlayService(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public Play createPlay(Play play) {
        return playRepository.save(play);
    }

    public Play getPlay(Long id) {
        Optional<Play> returnedPlay = playRepository.findById(id);
        if (returnedPlay.isPresent())
            return returnedPlay.get();
        else throw new PlayNotFoundException(id);
    }


    public List<Play> getPlays() {
        return playRepository.findAll();
    }

    public void deletePlay(Long id) {
        playRepository.deleteById(id);
    }
}
