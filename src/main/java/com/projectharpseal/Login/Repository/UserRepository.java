package com.projectharpseal.Login.Repository;

import com.projectharpseal.Login.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByKeyAndEmail(Long key, String email);
}
