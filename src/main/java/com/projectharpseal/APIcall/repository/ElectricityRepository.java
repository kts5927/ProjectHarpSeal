package com.projectharpseal.APIcall.repository;


import com.projectharpseal.APIcall.Entity.Electricity;
import com.projectharpseal.APIcall.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElectricityRepository extends JpaRepository<Electricity, Long> {

}