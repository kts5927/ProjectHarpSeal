package com.projectharpseal.APIcall.controller;

import com.projectharpseal.APIcall.service.Location_CSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Public_controller {


    private static final Logger logger = LoggerFactory.getLogger(Location_CSV.class);


    @GetMapping("/test")
    public String getTest() {
        return "Backend connect complete!";
    }

}
