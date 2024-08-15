package com.projectharpseal.APIcall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class Public_service {

    private static final Logger logger = LoggerFactory.getLogger(Public_service.class);

    private final WebClient webClient;
    private final Environment env;

    @Value("#{'${KEPCO_Code}'.split(',')}")
    private List<String> kepcoCode;

    LocalDate now_day = LocalDate.now();
    LocalDate twoWeeksAgoFriday = now_day.minusWeeks(2).with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));
    LocalDate lastYearDay = now_day.minusYears(1);
    LocalDate LastFriday = twoWeeksAgoFriday.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

    String LF = LastFriday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String LY = lastYearDay.format(DateTimeFormatter.ofPattern("yyyy"));
    String MM = now_day.format(DateTimeFormatter.ofPattern("MM"));

    @Autowired
    public Public_service(WebClient.Builder webClientBuilder, Environment env) {
        this.webClient = webClientBuilder
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB로 설정
                        .build())
                .build();
        this.env = env;
    }

    private Mono<String> makeApiCall(String baseUrlKey, String pathKey, String... queryParams) {
        String baseUrl = env.getProperty(baseUrlKey);
        String path = env.getProperty(pathKey);
        String fullUrl = baseUrl + path + "?" + String.join("&", queryParams);

        logger.info("Making request to URL: {}", fullUrl); // 디버깅용

        return webClient.get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> logger.error("Error during API call", e));
    }

    public Mono<Tuple4<String, String, String, String>> KCA_HIRA_Api_URI() {
        return Mono.zip(
                makeApiCall("KCA_URL", "KCA_ProdPrice_Path", "goodInspectDay=" + LF, "entpId=100", "ServiceKey=" + env.getProperty("DATA_Key")),
                makeApiCall("KCA_URL", "KCA_ProdInfo_Path", "ServiceKey=" + env.getProperty("DATA_Key")),
                makeApiCall("KCA_URL", "KCA_StoreInfo_Path", "ServiceKey=" + env.getProperty("DATA_Key")),
                makeApiCall("HIRA_URL", "HIRA_HospBase_Path", "ServiceKey=" + env.getProperty("DATA_Key"))
        );
    }

    public Flux<String> KEPCO_Api_URI() {
        return Flux.fromIterable(kepcoCode)
                .concatMap(code -> {
                    logger.info("Making API call for metroCd: {}", code);
                    return makeApiCall("KEPCO_URL", "KEPCO_Path", "year=" + LY, "month=" + MM, "metroCd=" + code, "apiKey=" + env.getProperty("KEPCO_Key"))
                            .doOnNext(response -> logger.info("Received response: {}", response))
                            .doOnError(error -> logger.error("Error during KEPCO API call", error));
                });
    }

}
