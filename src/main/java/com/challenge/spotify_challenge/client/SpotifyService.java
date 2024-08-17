package com.challenge.spotify_challenge.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SpotifyService {

    private static final String BASE_URL = "https://api.spotify.com/v1";
    private static final String TOKEN = "YOUR_SPOTIFY_ACCESS_TOKEN";

    private final RestTemplate restTemplate;

    @Autowired
    public SpotifyService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Map<String, Object> getTrackMetadata(String isrc) {
        String url = BASE_URL + "/search?q=isrc:" + isrc + "&type=track";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> trackData = (Map<String, Object>) response.getBody().get("tracks");
        return (Map<String, Object>) trackData.get("items");
    }

}
