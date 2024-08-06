package com.projectharpseal.APIcall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@PropertySource("classpath:api.properties")
public class Public_config {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .filter(logRequest())
                .build();

    }

    private ExchangeFilterFunction logRequest() {

        return ExchangeFilterFunction.ofRequestProcessor(clientRequest ->{
            System.out.println("Request : " + clientRequest.method() + " " + clientRequest.url());
            return Mono.just(clientRequest);
                });

    }

}
