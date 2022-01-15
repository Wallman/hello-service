package com.wallman.helloservice.facade;

import com.wallman.helloservice.domain.Greeting;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GreetingsFacade {

    private static final String GREETING_SERVICE_URL = "https://greeting-message-m6cqmyea3a-lz.a.run.app/message";

    public Mono<Greeting> get() {
        WebClient client = WebClient.create();
        return client.get()
                .uri(GREETING_SERVICE_URL)
                .retrieve()
                .bodyToMono(Greeting.class);
    }
}
