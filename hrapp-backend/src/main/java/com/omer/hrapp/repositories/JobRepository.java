package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
