package com.omer.hrapp.repositories;

import com.omer.hrapp.entities.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
}
