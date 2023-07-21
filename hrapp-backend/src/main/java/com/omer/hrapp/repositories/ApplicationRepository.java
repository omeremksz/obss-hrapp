package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
