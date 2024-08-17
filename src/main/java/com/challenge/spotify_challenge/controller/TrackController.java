package com.challenge.spotify_challenge.controller;

import com.challenge.spotify_challenge.entity.Track;
import com.challenge.spotify_challenge.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/createTrack")
    public ResponseEntity<?> createTrack(@RequestParam String isrc) {
        try {
            trackService.createTrack(isrc);
            return ResponseEntity.status(HttpStatus.CREATED.value()).body("Track created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(e.getMessage());
        }
    }

    @GetMapping("/getCover")
    public ResponseEntity<?> getCover(@RequestParam String isrc) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(trackService.getCover(isrc));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with the image".getBytes());
        }
    }
}