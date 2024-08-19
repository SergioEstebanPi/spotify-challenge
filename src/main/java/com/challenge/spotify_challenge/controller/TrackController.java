package com.challenge.spotify_challenge.controller;

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
    public ResponseEntity<?> getTrackMetadata(@RequestParam String isrc) {
        try {
            return ResponseEntity.ok(trackService.getTrackMetadata(isrc));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Couldn't get data");
        }
    }

    @PostMapping("/createTrack")
    public ResponseEntity<?> createTrack(@RequestParam String isrc) {
        try {
            trackService.createTrack(isrc);
            return ResponseEntity.status(HttpStatus.CREATED.value()).body("Track created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Track not found");
        }
    }

    @GetMapping(value="/getCover", produces=MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getCover(@RequestParam String isrc) {
        try {
            return ResponseEntity.ok().body(trackService.getCover(isrc));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error with the image".getBytes());
        }
    }
}