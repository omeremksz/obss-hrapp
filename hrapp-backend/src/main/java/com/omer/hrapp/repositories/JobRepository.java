package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByJobCategoryIdAndJobPositionId(Long jobCategoryId, Long jobPositionId);

    List<Job> findByJobCategoryId(Long jobCategoryId);

    List<Job> findByJobPositionId(Long jobPositionId);
}
