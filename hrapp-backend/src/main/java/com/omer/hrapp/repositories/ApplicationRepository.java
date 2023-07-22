package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicantId(Optional<Long> applicantId);
}
