package com.projectharpseal.APIcall.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Electricity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long electricity_key;

    private String electricityYear;
    private String electricityMonth;
    private int electricityPopulation;
    private double electricityAverageUsage;
    private int electricityAverageBill;
    private Long locationKey;

    // 빌더 패턴을 위한 생성자
    @Builder
    public Electricity(String electricityYear, String electricityMonth, int electricityPopulation, double electricityAverageUsage, int electricityAverageBill, Long locationKey) {
        this.electricityYear = electricityYear;
        this.electricityMonth = electricityMonth;
        this.electricityPopulation = electricityPopulation;
        this.electricityAverageUsage = electricityAverageUsage;
        this.electricityAverageBill = electricityAverageBill;
        this.locationKey = locationKey;
    }
}