package com.omer.hrapp.services;

import com.omer.hrapp.entities.Job;
import com.omer.hrapp.entities.JobCategory;
import com.omer.hrapp.entities.JobPosition;
import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.repositories.JobRepository;
import com.omer.hrapp.requests.JobCreateRequest;
import com.omer.hrapp.requests.JobUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    JobRepository jobRepository;

    JobCategoryService jobCategoryService;

    JobPositionService jobPositionService;

    SpecialistService specialistService;

    public JobService(JobRepository jobRepository, JobCategoryService jobCategoryService, JobPositionService jobPositionService, SpecialistService specialistService) {
        this.jobRepository = jobRepository;
        this.jobCategoryService = jobCategoryService;
        this.jobPositionService = jobPositionService;
        this.specialistService = specialistService;
    }

    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    public List<Job> getAllJobs(Optional<Long> jobCategoryId, Optional<Long> jobPositionId) {
        if(jobCategoryId.isPresent() && jobPositionId.isPresent()){
            return jobRepository.findByJobCategoryIdAndJobPositionId(jobCategoryId.get(), jobPositionId.get());
        } else if (jobCategoryId.isPresent()) {
            return jobRepository.findByJobCategoryId(jobCategoryId.get());
        } else if (jobPositionId.isPresent()) {
            return jobRepository.findByJobPositionId(jobPositionId.get());
        }else
            return jobRepository.findAll();
    }

    public Job createNewJob(JobCreateRequest jobCreateRequest) {
        Specialist specialist = specialistService.getSpecialistById(jobCreateRequest.getSpecialistId());
        JobCategory jobCategory = jobCategoryService.getJobCategoryById(jobCreateRequest.getJobCategoryId());
        JobPosition jobPosition = jobPositionService.getJobPositionById(jobCreateRequest.getJobPositionId());
        if(specialist == null || jobCategory == null || jobPosition == null){
            return null;
        }
        Job toSave = new Job();
        toSave.setId(jobCreateRequest.getId());
        toSave.setCode(jobCreateRequest.getCode());
        toSave.setTitle(jobCreateRequest.getTitle());
        toSave.setLocation(jobCreateRequest.getLocation());
        toSave.setDescription(jobCreateRequest.getDescription());
        toSave.setActivationTime(jobCreateRequest.getActivationTime());
        toSave.setDeactivationTime(jobCreateRequest.getDeactivationTime());
        toSave.setApplicantsCount(jobCreateRequest.getApplicantsCount());
        toSave.setSpecialist(specialist);
        toSave.setJobCategory(jobCategory);
        toSave.setJobPosition(jobPosition);
        return jobRepository.save(toSave);
    }

    public Job updateJob(Long jobId, JobUpdateRequest jobUpdateRequest) {
        Optional<Job> job = jobRepository.findById(jobId);
        JobCategory jobCategory = jobCategoryService.getJobCategoryById(jobUpdateRequest.getJobCategoryId());
        JobPosition jobPosition = jobPositionService.getJobPositionById(jobUpdateRequest.getJobPositionId());
        if (job.isPresent()){
            Job toUpdate = job.get();
            toUpdate.setCode(jobUpdateRequest.getCode());
            toUpdate.setTitle(jobUpdateRequest.getTitle());
            toUpdate.setLocation(jobUpdateRequest.getLocation());
            toUpdate.setDescription(jobUpdateRequest.getDescription());
            toUpdate.setActivationTime(jobUpdateRequest.getActivationTime());
            toUpdate.setDeactivationTime(jobUpdateRequest.getDeactivationTime());
            toUpdate.setApplicantsCount(jobUpdateRequest.getApplicantsCount());
            toUpdate.setJobCategory(jobCategory);
            toUpdate.setJobPosition(jobPosition);
            return jobRepository.save(toUpdate);
        }else
            return null;
    }

    public void deleteJobById(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}
