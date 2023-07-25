package com.omer.hrapp.services;

import com.omer.hrapp.entities.JobCategory;
import com.omer.hrapp.repositories.JobCategoryRepository;
import com.omer.hrapp.requests.JobCategoryCreateRequest;
import com.omer.hrapp.requests.JobCategoryUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobCategoryService {

    JobCategoryRepository jobCategoryRepository;

    public JobCategoryService(JobCategoryRepository jobCategoryRepository) {
        this.jobCategoryRepository = jobCategoryRepository;
    }

    public List<JobCategory> getAllJobCategories() {
        return jobCategoryRepository.findAll();
    }

    public JobCategory getJobCategoryById(Long jobCategoryId) {
        return jobCategoryRepository.findById(jobCategoryId).orElse(null);
    }

    public JobCategory createNewJobCategory(JobCategoryCreateRequest jobCategoryCreateRequest) {
        JobCategory toSave = new JobCategory();
        toSave.setId(jobCategoryCreateRequest.getId());
        toSave.setCode(jobCategoryCreateRequest.getCode());
        toSave.setName(jobCategoryCreateRequest.getName());
        toSave.setDescription(jobCategoryCreateRequest.getDescription());
        return jobCategoryRepository.save(toSave);
    }

    public JobCategory updateJobCategory(Long jobCategoryId, JobCategoryUpdateRequest jobCategoryUpdateRequest) {
        Optional<JobCategory> jobCategory = jobCategoryRepository.findById(jobCategoryId);
        if(jobCategory.isPresent()){
            JobCategory toUpdate = jobCategory.get();
            toUpdate.setCode(jobCategoryUpdateRequest.getCode());
            toUpdate.setName(jobCategoryUpdateRequest.getName());
            toUpdate.setDescription(jobCategoryUpdateRequest.getDescription());
            return jobCategoryRepository.save(toUpdate);
        }else
            return null;
    }

    public void deleteJobCategoryById(Long jobCategoryId) {
        jobCategoryRepository.deleteById(jobCategoryId);
    }
}
