package com.wallman.helloservice.controller;

import com.wallman.helloservice.facade.GreetingsFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    private final GreetingsFacade greetingsFacade;


    public HelloController(GreetingsFacade greetingsFacade) {
        this.greetingsFacade = greetingsFacade;
    }

    @GetMapping("/greeting")
    public Mono<String> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return greetingsFacade.get()
                .map(greeting -> String.format("%s, %s!", greeting.getMessage(), name));
    }
}
