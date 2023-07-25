package com.omer.hrapp.services;

import com.omer.hrapp.entities.JobCategory;
import com.omer.hrapp.entities.JobPosition;
import com.omer.hrapp.repositories.JobPositionRepository;
import com.omer.hrapp.requests.JobPositionCreateRequest;
import com.omer.hrapp.requests.JobPositionUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobPositionService {

    JobPositionRepository jobPositionRepository;

    public JobPositionService(JobPositionRepository jobPositionRepository) {
        this.jobPositionRepository = jobPositionRepository;
    }

    public List<JobPosition> getAllJobPositions() {
        return jobPositionRepository.findAll();
    }

    public JobPosition getJobPositionById(Long jobPositionId) {
        return jobPositionRepository.findById(jobPositionId).orElse(null);
    }

    public JobPosition createNewJobPosition(JobPositionCreateRequest jobPositionCreateRequest) {
        JobPosition toSave = new JobPosition();
        toSave.setId(jobPositionCreateRequest.getId());
        toSave.setCode(jobPositionCreateRequest.getCode());
        toSave.setName(jobPositionCreateRequest.getName());
        toSave.setDescription(jobPositionCreateRequest.getDescription());
        return jobPositionRepository.save(toSave);
    }

    public JobPosition updateJobPosition(Long jobPositionId, JobPositionUpdateRequest jobPositionUpdateRequest) {
        Optional<JobPosition> jobPosition = jobPositionRepository.findById(jobPositionId);
        if(jobPosition.isPresent()){
            JobPosition toUpdate = jobPosition.get();
            toUpdate.setCode(jobPositionUpdateRequest.getCode());
            toUpdate.setName(jobPositionUpdateRequest.getName());
            toUpdate.setDescription(jobPositionUpdateRequest.getDescription());
            return jobPositionRepository.save(toUpdate);
        }else
            return null;
    }

    public void deleteJobCategoryById(Long jobPositionId) {
        jobPositionRepository.deleteById(jobPositionId);
    }
}
