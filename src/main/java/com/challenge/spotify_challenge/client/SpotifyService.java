package com.challenge.spotify_challenge.client;

import com.challenge.spotify_challenge.config.SpotifyConfig;
import com.challenge.spotify_challenge.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpotifyService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final TokenService tokenService;

    @Autowired
    public SpotifyService(SpotifyConfig spotifyConfig, RestTemplateBuilder restTemplateBuilder, TokenService tokenService) {
        this.restTemplate = restTemplateBuilder.build();
        this.tokenService = tokenService;
        baseUrl = spotifyConfig.getBaseUrl();
    }

    public Map<String, Object> getTrackMetadata(String isrc) {
        String url = baseUrl + "/search?q=isrc:" + isrc + "&type=track";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getToken("token"));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> trackData = (Map<String, Object>) response.getBody().get("tracks");
        List<Map<String, Object>> items = (List<Map<String, Object>>) trackData.get("items");
        Map<String, Object> track = items.get(0);
        Map<String, Object> album = (Map<String, Object>) track.get("album");
        List<Map<String, Object>> artists = (List<Map<String, Object>>) album.get("artists");

        Map<String, Object> artistData = artists.get(0);
        String artistName = (String) artistData.get("name");
        String name = (String) track.get("name");
        String albumName = (String) album.get("name");
        String albumId = (String) album.get("id");
        Boolean explicit = (Boolean) track.get("explicit");
        Integer duration_ms = (Integer) track.get("duration_ms");

        Map<String, Object> trackMetaData = new HashMap<>();
        trackMetaData.put("name", name);
        trackMetaData.put("artistName", artistName);
        trackMetaData.put("albumName", albumName);
        trackMetaData.put("albumId", albumId);
        trackMetaData.put("isExplicit", explicit);
        trackMetaData.put("playbackSeconds", duration_ms / 1000);
        return trackMetaData;
    }

    public Map<String, Object> getAlbumCover(String albumId) {
        String url = baseUrl + "/albums/" + albumId;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getToken("token"));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> albumData = (Map<String, Object>) response.getBody();
        List<Map<String, Object>> images = (List<Map<String, Object>>) albumData.get("images");
        return images.get(0);
    }

}
