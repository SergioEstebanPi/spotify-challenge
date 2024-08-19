package com.challenge.spotify_challenge.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public void saveToken(String serviceName, String token) {
        tokenStore.put(serviceName, token);
    }

    public String getToken(String serviceName) {
        return tokenStore.get(serviceName);
    }

    public void clearToken(String serviceName) {
        tokenStore.remove(serviceName);
    }
}
