package com.projectharpseal.APIcall.repository;


import com.projectharpseal.APIcall.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
