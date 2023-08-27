package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByApplicantIdAndJobId(Long applicantId, Long jobId);

    List<Application> findByApplicantId(Long applicantId);

    List<Application> findByJobId(Long jobId);
}
