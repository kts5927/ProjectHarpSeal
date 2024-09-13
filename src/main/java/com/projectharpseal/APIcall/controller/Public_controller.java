package com.projectharpseal.APIcall.controller;

import com.projectharpseal.APIcall.Entity.Electricity;
import com.projectharpseal.APIcall.service.Location_CSV;
import com.projectharpseal.APIcall.service.RandomElectricity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Public_controller {


    private static final Logger logger = LoggerFactory.getLogger(Location_CSV.class);
    private final RandomElectricity randomElectricity;


    public Public_controller(RandomElectricity randomElectricity) {
        this.randomElectricity = randomElectricity;
    }

    @GetMapping("/test")
    public String getTest() {
        return "Backend connect complete!";
    }

    @GetMapping("/random")
    public Electricity getRandomElectricity() {
        return randomElectricity.getRandomElectricity();
    }

}
