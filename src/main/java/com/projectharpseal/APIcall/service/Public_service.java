package com.projectharpseal.APIcall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class Public_service {

    private static final Logger logger = LoggerFactory.getLogger(Public_service.class);

    private final WebClient webClient;
    private final Environment env;

    @Autowired
    public Public_service(WebClient webClient, Environment env) {
        this.webClient = webClient;
        this.env = env;

    }

    public Mono<String> getProductPriceInfo() {
        String baseUrl = env.getProperty("baseUrl");
        String publicPath = env.getProperty("publicPath");
        String queryParams = env.getProperty("queryParams");
        String fullUrl = baseUrl + publicPath + "?" + queryParams;

        logger.info("Sending request to URL: {}", fullUrl);

        return webClient.get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.error("Error during API call", e));
    }
}
