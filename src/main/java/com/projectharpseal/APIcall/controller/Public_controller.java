package com.projectharpseal.APIcall.controller;

import com.projectharpseal.APIcall.service.Public_service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Public_controller {

    private final Public_service publicService;

    public Public_controller(Public_service publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/product-price")
    public Mono<String> getProductPriceInfo() {
        return publicService.getProductPriceInfo();
    }
}
