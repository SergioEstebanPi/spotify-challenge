package com.challenge.spotify_challenge.config;

import com.challenge.spotify_challenge.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DBConfig dbConfig;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(DBConfig dbConfig, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.dbConfig = dbConfig;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.withUsername(dbConfig.getUserName())
            .password("{noop}" + dbConfig.getPassword())
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**", "/login/**", "/codechallenge/**", "/api/**, ").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(oauth2UserService())
                                )
                                .successHandler(customAuthenticationSuccessHandler)
                                .failureUrl("/login?error")
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/")
                                .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());;
        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new OAuth2UserService<OAuth2UserRequest, OAuth2User>() {
            @Override
            public OAuth2User loadUser(OAuth2UserRequest userRequest) {
                OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
                Map<String, Object> attributes = oAuth2User.getAttributes();
                String displayName = (String) attributes.get("display_name");
                if (displayName == null) {
                    throw new IllegalArgumentException("Display name attribute is missing");
                }
                return new DefaultOAuth2User(
                        Collections.singleton(new SimpleGrantedAuthority("USER")),
                        attributes,
                        "display_name"
                );
            }
        };
    }
}
