package com.wallman.helloservice;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Configuration
public class SpringConfiguration {

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .filter(ExchangeFilterFunction.ofRequestProcessor(this::injectAuthToken))
                .build();
    }

    private Mono<ClientRequest> injectAuthToken(ClientRequest clientRequest) {
        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            credentials.refreshIfExpired();
            String token = credentials.getAccessToken().getTokenValue();
            return Mono.just(ClientRequest.from(clientRequest)
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
