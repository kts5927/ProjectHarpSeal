package com.projectharpseal.APIcall.controller;

import com.projectharpseal.APIcall.service.KEPCO;
import com.projectharpseal.APIcall.service.Location_CSV;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Update_controller {

    private final Location_CSV locationCsv;
    private final KEPCO kepco;

    public Update_controller(Location_CSV locationCsv, KEPCO kepco) {
        this.locationCsv = locationCsv;
        this.kepco = kepco;
    }

    @PostMapping("/update_Location")
    public void data(@RequestParam("file") MultipartFile file) throws IOException {
        locationCsv.ExcelData(file);
    }

    @GetMapping("/update_KEPCO")
    public void KEPCO_update() {
        kepco.parseApiCall();
    }
}
