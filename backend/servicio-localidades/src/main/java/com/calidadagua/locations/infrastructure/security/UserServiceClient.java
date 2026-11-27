package com.calidadagua.locations.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.core.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.util.UUID;

@Component
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(
            @Value("${users.base-url}") String baseUrl) {

        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    public UserProfile getProfile(String token) {

        try {

            UserProfile profile = restClient.get()
                    .uri("/api/auth/profile")
                    .headers(headers ->
                            headers.setBearerAuth(token))
                    .retrieve()
                    .body(UserProfile.class);

            if (profile == null || !profile.active()) {
                throw invalidToken();
            }

            return profile;

        } catch (RestClientResponseException ex) {

            if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED
                    || ex.getStatusCode() == HttpStatus.FORBIDDEN) {

                throw invalidToken();
            }

            throw serviceUnavailable();

        } catch (RestClientException ex) {

            throw serviceUnavailable();
        }
    }

    private OAuth2AuthenticationException invalidToken() {

        return new OAuth2AuthenticationException(
                new OAuth2Error(
                        "invalid_token",
                        "User is not authorized",
                        null
                )
        );
    }

    private OAuth2AuthenticationException serviceUnavailable() {

        return new OAuth2AuthenticationException(
                new OAuth2Error(
                        "temporarily_unavailable",
                        "User service is unavailable",
                        null
                )
        );
    }

    public record UserProfile(
            UUID id,
            String role,
            boolean active
    ) {
    }
}