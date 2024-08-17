package com.challenge.spotify_challenge.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class DBConfig {

    @Value("${db.username}")
    private String userName;

    @Value("${db.password}")
    private String password;
}
