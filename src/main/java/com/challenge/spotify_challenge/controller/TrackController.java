package com.challenge.spotify_challenge.controller;

import com.challenge.spotify_challenge.entity.Track;
import com.challenge.spotify_challenge.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codechallenge")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @GetMapping("/getTrackMetadata")
    public ResponseEntity<Track> getTrackMetadata(@RequestParam String isrc) {
        return trackService.getTrackMetadata(isrc)
            .map(track -> ResponseEntity.ok(track))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}