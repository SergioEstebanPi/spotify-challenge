package com.challenge.spotify_challenge.service;

import com.challenge.spotify_challenge.client.SpotifyService;
import com.challenge.spotify_challenge.entity.Track;
import com.challenge.spotify_challenge.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private SpotifyService spotifyService;

    public Optional<Track> getTrackMetadata(String isrc) throws Exception {
        Map<String, Object> trackData = spotifyService.getTrackMetadata(isrc);
        if (trackData.isEmpty()) {
            throw new Exception("Track not found.");
        }
        return trackRepository.findByIsrc(isrc);
    }

    public void createTrack(String isrc) throws Exception {
        Optional<Track> existingTrack = trackRepository.findByIsrc(isrc);
        if (existingTrack.isPresent()) {
            return;
        }

        Map<String, Object> trackData = spotifyService.getTrackMetadata(isrc);
        if (trackData.isEmpty()) {
            throw new Exception("Track not found.");
        }

        Map<String, Object> coverData = spotifyService.getAlbumCover((String) trackData.get("albumId"));

        Track track = Track.builder()
            .isrc(isrc)
            .name((String) trackData.get("name"))
            .artistName((String) trackData.get("artistName"))
            .albumName((String) trackData.get("albumName"))
            .albumId((String) trackData.get("albumId"))
            .isExplicit((Boolean) trackData.get("isExplicit"))
            .playbackSeconds((Integer) trackData.get("playbackSeconds"))
            .coverImageUrl((String) coverData.get("url"))
            .build();
        trackRepository.save(track);
    }

    public byte[] getCover(String isrc) throws Exception {
        Optional<Track> track = trackRepository.findByIsrc(isrc);
        if(track.isEmpty()) {
            throw new Exception("Track not found.");
        } else {
            try {
                URL url = new URL(track.get().getCoverImageUrl());
                BufferedInputStream bios = new BufferedInputStream(url.openStream());
                return bios.readAllBytes();
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        }
    }
}
