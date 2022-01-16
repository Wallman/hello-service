package com.wallman.helloservice.facade;

import com.wallman.helloservice.domain.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GreetingsFacade {

    private static final String GREETING_SERVICE_URL = "https://greeting-message-m6cqmyea3a-lz.a.run.app/message";
    private final WebClient client;

    public GreetingsFacade(@Autowired WebClient client) {
        this.client = client;
    }

    public Mono<Greeting> get() {
        return client.get()
                .uri(GREETING_SERVICE_URL)
                .retrieve()
                .bodyToMono(Greeting.class);
    }
}
