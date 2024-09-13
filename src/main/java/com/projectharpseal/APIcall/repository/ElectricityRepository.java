package com.projectharpseal.APIcall.repository;


import com.projectharpseal.APIcall.Entity.Electricity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ElectricityRepository extends JpaRepository<Electricity, Long> {

    @Query(value = "SELECT * FROM electricity ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Electricity> findRandomEntity();


}