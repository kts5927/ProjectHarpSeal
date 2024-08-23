package com.projectharpseal.APIcall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class Public_service {

    private static final Logger logger = LoggerFactory.getLogger(Public_service.class);
    private final WebClient webClient;
    private final Environment env;

    @Autowired
    public Public_service(Environment env, WebClient.Builder webClientBuilder) {
        this.env = env;
        this.webClient = webClientBuilder
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB로 설정
                        .build())
                .build();
    }

    public Mono<String> makeApiCall(String baseUrlKey, String pathKey, String keyParam, String... queryParams) {
        String baseUrl = env.getProperty(baseUrlKey);
        String path = env.getProperty(pathKey);
        String key = env.getProperty(keyParam);
        String fullUrl = baseUrl + path + "?" + String.join("&", queryParams) + key;
        //Todo
        // logger 없애기
        logger.info("Making request to URL: {}", fullUrl);

        return webClient.get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.error("Error during API call", e));
    }
}
