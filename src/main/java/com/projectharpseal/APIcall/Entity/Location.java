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
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationKey;

    private String locationCity;
    private String locationCountry;
    private String locationTown;
    private double locationX;
    private double locationY;

    // 빌더 패턴을 위한 생성자
    @Builder
    public Location(String locationCity, String locationCountry, String locationTown, double locationX, double locationY) {
        this.locationCity = locationCity;
        this.locationCountry = locationCountry;
        this.locationTown = locationTown;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
