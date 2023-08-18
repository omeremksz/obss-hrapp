package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Optional<Specialist> findByUserName(String username);
}
