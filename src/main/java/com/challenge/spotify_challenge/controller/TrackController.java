package com.challenge.spotify_challenge.controller;

import com.challenge.spotify_challenge.entity.Track;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codechallenge")
public class TrackController {

    @GetMapping("/getTrackMetadata")
    public ResponseEntity<Track> getTrackMetadata(@RequestParam String isrc) {
        Track track = Track.builder()
                .id(1L)
                .name("track name")
                .albumId("1")
                .albumName("album name")
                .artistName("artist")
                .build();
        return new ResponseEntity<>(track, HttpStatus.OK);
    }
}