package com.challenge.spotify_challenge.service;

import com.challenge.spotify_challenge.entity.Track;
import com.challenge.spotify_challenge.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public Optional<Track> getTrackMetadata(String isrc) {
        return trackRepository.findByIsrc(isrc);
    }

}
