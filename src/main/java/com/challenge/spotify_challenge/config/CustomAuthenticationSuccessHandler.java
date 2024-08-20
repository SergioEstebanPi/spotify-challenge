package com.challenge.spotify_challenge.config;

import com.challenge.spotify_challenge.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final SpotifyConfig spotifyConfig;

    public CustomAuthenticationSuccessHandler(TokenService tokenService, OAuth2AuthorizedClientService authorizedClientService, SpotifyConfig spotifyConfig) {
        this.authorizedClientService = authorizedClientService;
        this.tokenService = tokenService;
        this.spotifyConfig = spotifyConfig;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        try {
            String clientId = "spotify";
            String username = authentication.getName();
            OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientId, username);
            if (authorizedClient != null) {
                String accessToken = authorizedClient.getAccessToken().getTokenValue();
                tokenService.saveToken("token", accessToken);
                request.getSession().setAttribute("accessToken", accessToken);
            }
            response.sendRedirect(spotifyConfig.getAppFrontUrl());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to obtain access token");
        }
    }
}
