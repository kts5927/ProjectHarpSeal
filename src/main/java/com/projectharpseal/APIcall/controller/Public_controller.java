package com.projectharpseal.APIcall.controller;

import com.projectharpseal.APIcall.service.Public_service;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

@RestController
public class Public_controller {

    private final Public_service publicService;

    public Public_controller(Public_service publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/KCA_HIRA")
    public Mono<Tuple4< String, String, String, String>> getProductPriceInfo() {
        return publicService.KCA_HIRA_Api_URI();
    }

    @GetMapping(value = "/KEPCO", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getKEPCO_Data() {
        return publicService.KEPCO_Api_URI();
    }


}
