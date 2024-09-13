package com.projectharpseal.APIcall.service;

import com.projectharpseal.APIcall.Entity.Electricity;
import com.projectharpseal.APIcall.repository.ElectricityRepository;
import org.springframework.stereotype.Service;

@Service
public class RandomElectricity {
    private final ElectricityRepository electricityRepository;

    public RandomElectricity(ElectricityRepository electricityRepository) {
        this.electricityRepository = electricityRepository;
    }

    public Electricity getRandomElectricity() {
        return electricityRepository.findRandomEntity()
                .orElseThrow(() -> new RuntimeException("랜덤한 데이터를 찾을 수 없습니다."));
    }
}
