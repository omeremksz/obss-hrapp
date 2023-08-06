package com.omer.hrapp.services;

import com.omer.hrapp.entities.*;
import com.omer.hrapp.repositories.JobRepository;
import com.omer.hrapp.requests.JobCreateRequest;
import com.omer.hrapp.requests.JobUpdateRequest;
import com.omer.hrapp.responses.ApplicationResponse;
import com.omer.hrapp.responses.JobResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    JobRepository jobRepository;

    SpecialistService specialistService;

    ApplicationService applicationService;

    public JobService(JobRepository jobRepository, SpecialistService specialistService, ApplicationService applicationService) {
        this.jobRepository = jobRepository;
        this.specialistService = specialistService;
        this.applicationService = applicationService;
    }

    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    public JobResponse getJobByIdWithApplications(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        List<ApplicationResponse> jobApplications = applicationService.gelAllApplications(Optional.ofNullable(null), Optional.of(jobId));
        return new JobResponse(job, jobApplications);
    }

    public List<JobResponse> getAllJobsBySpecialistId( Optional<Long> specialistId ) {
        List<Job> jobList;
        if (specialistId.isPresent()) {
            jobList = jobRepository.findBySpecialistId(specialistId.get());
        }else
            jobList = jobRepository.findAll();
        return jobList.stream().map(j -> new JobResponse(j)).collect(Collectors.toList());
    }

    public Job createNewJob(JobCreateRequest jobCreateRequest) {
        Specialist specialist = specialistService.getSpecialistById(jobCreateRequest.getSpecialistId());
        if(specialist == null){
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
        toSave.setJobCategory(jobCreateRequest.getJobCategory());
        toSave.setJobPosition(jobCreateRequest.getJobPosition());
        toSave.setSpecialist(specialist);
        return jobRepository.save(toSave);
    }

    public Job updateJob(Long jobId, JobUpdateRequest jobUpdateRequest) {
        Optional<Job> job = jobRepository.findById(jobId);
        if (job.isPresent()){
            Job toUpdate = job.get();
            toUpdate.setCode(jobUpdateRequest.getCode());
            toUpdate.setTitle(jobUpdateRequest.getTitle());
            toUpdate.setLocation(jobUpdateRequest.getLocation());
            toUpdate.setDescription(jobUpdateRequest.getDescription());
            toUpdate.setActivationTime(jobUpdateRequest.getActivationTime());
            toUpdate.setDeactivationTime(jobUpdateRequest.getDeactivationTime());
            toUpdate.setJobCategory(jobUpdateRequest.getJobCategoryName());
            toUpdate.setJobPosition(jobUpdateRequest.getJobPositionName());
            return jobRepository.save(toUpdate);
        }else
            return null;
    }

    public void deleteJobById(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}
