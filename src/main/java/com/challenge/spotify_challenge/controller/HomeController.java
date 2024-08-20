package com.challenge.spotify_challenge.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping()
    public String home() {
        return "Welcome to the Spotify OAuth2 Demo!";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User user) {
        if (user != null) {
            return "Login Successful! Hello, " + user.getName() + "!";
        } else {
            return "Login Successful, but no user details available.";
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "Login failed. Please try again.";
    }
}