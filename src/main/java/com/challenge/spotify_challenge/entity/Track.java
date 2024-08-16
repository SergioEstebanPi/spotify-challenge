package com.challenge.spotify_challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isrc;
    private String name;
    private String artistName;
    private String albumName;
    private String albumId;
    private boolean isExplicit;
    private int playbackSeconds;
    private String coverImageUrl;

    public Track() {}

}

