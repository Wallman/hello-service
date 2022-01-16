package com.wallman.helloservice.facade;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.wallman.helloservice.domain.Greeting;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class GreetingsFacade {

    private static final String GREETING_SERVICE_URL = "https://greeting-message-m6cqmyea3a-lz.a.run.app/message";

    public Mono<Greeting> get() {
        try {
            HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(GoogleCredentials.getApplicationDefault());
            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(credentialsAdapter);
            HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(GREETING_SERVICE_URL));
            String response = request.execute().parseAsString();
            Greeting greeting = new Greeting();
            greeting.setMessage(response);
            return Mono.just(greeting);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
