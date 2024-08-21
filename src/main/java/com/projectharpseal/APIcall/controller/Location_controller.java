package com.projectharpseal.APIcall.controller;

import com.projectharpseal.APIcall.service.Location_CSV;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Location_controller {

    private final Location_CSV locationCsv;
    public Location_controller(Location_CSV locationCsv) {
        this.locationCsv = locationCsv;
    }

    @PostMapping("/update")
    public void data(@RequestParam("file") MultipartFile file) throws IOException {
        locationCsv.ExcelData(file);
    }
}
