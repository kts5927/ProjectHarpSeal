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
    private Long location_key;

    private String location_city;
    private String location_country;
    private String location_town;
    private double location_x;
    private double location_y;

    // 빌더 패턴을 위한 생성자
    @Builder
    public Location(String location_city, String location_country, String location_town, double location_x, double location_y) {
        this.location_city = location_city;
        this.location_country = location_country;
        this.location_town = location_town;
        this.location_x = location_x;
        this.location_y = location_y;
    }
}
