package com.challenge.spotify_challenge.service;

import com.challenge.spotify_challenge.client.SpotifyService;
import com.challenge.spotify_challenge.entity.Track;
import com.challenge.spotify_challenge.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private SpotifyService spotifyService;

    public Optional<Track> getTrackMetadata(String isrc) {
        return trackRepository.findByIsrc(isrc);
    }

    public void createTrack(String isrc) throws Exception {
        if (trackRepository.findByIsrc(isrc).isPresent()) {
            throw new Exception("Track already exists.");
        }

        Map<String, Object> trackData = spotifyService.getTrackMetadata(isrc);
        if (trackData.isEmpty()) {
            throw new Exception("Track not found.");
        }

        Track track = Track.builder()
            .isrc(isrc)
            .name((String) trackData.get("name"))
            .artistName((String) trackData.get("artistName"))
            .albumName((String) trackData.get("albumName"))
            .albumId((String) trackData.get("albumId"))
            .isExplicit((Boolean) trackData.get("isExplicit"))
            .playbackSeconds((Integer) trackData.get("playbackSeconds"))
            .build();
        trackRepository.save(track);
    }

}
