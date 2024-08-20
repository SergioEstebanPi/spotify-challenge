package com.challenge.spotify_challenge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class SpotifyConfig {

    @Value("${spotify.base-url}")
    private String baseUrl;

    @Value("${app.base.url}")
    private String appBaseUrl;

    @Value("${app.front.url.search}")
    private String appFrontUrl;
}
