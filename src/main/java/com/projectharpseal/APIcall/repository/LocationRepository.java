package com.projectharpseal.APIcall.repository;


import com.projectharpseal.APIcall.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLocationCityAndLocationCountryAndLocationTown(String locationCity, String locationCountry, String locationTown);

}