package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Specialist findByUserName(String username);
}
