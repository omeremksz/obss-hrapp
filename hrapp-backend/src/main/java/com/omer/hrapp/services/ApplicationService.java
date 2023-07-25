package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.entities.Application;
import com.omer.hrapp.entities.Job;
import com.omer.hrapp.repositories.ApplicationRepository;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import com.omer.hrapp.requests.ApplicationUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private ApplicationRepository applicationRepository;
    private ApplicantService applicantService;
    private JobService jobService;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicantService applicantService, JobService jobService) {
        this.applicationRepository = applicationRepository;
        this.applicantService = applicantService;
        this.jobService = jobService;
    }

    public List<Application> gelAllApplications(Optional<Long> applicantId, Optional<Long> jobId) {
        if(applicantId.isPresent() && jobId.isPresent()){
            return applicationRepository.findByApplicantIdAndJobId(applicantId.get(), jobId.get());
        } else if (applicantId.isPresent()) {
            return applicationRepository.findByApplicantId(applicantId.get());
        } else if (jobId.isPresent()) {
            return applicationRepository.findByJobId(jobId.get());
        } else return applicationRepository.findAll();
    }

    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId).orElse(null);
    }

    public Application createNewApplication(ApplicationCreateRequest applicationCreateRequest) {
        Applicant applicant = applicantService.getApplicantById(applicationCreateRequest.getApplicantId());
        Job job = jobService.getJobById(applicationCreateRequest.getJobId());

        if(applicant == null || job == null){
            return null;
        }
        Application toSave = new Application();
        toSave.setId(applicationCreateRequest.getId());
        toSave.setAppliedDate(LocalDateTime.now());
        toSave.setApplicationStatus(applicationCreateRequest.getApplicationStatus());
        toSave.setLastUpdateTime(applicationCreateRequest.getLastUpdateTime());
        toSave.setApplicant(applicant);
        toSave.setJob(job);
        return applicationRepository.save(toSave);
    }

    public Application updateApplicationById(Long applicationId, ApplicationUpdateRequest updateApplicationRequest){
        Optional<Application> application = applicationRepository.findById(applicationId);
        if(application.isPresent()){
            Application toUpdate = application.get();
            toUpdate.setApplicationStatus(updateApplicationRequest.getApplicationStatus());
            toUpdate.setLastUpdateTime(updateApplicationRequest.getLastUpdateTime());
            return applicationRepository.save(toUpdate);
        }else
            return null;
    }

    public void deleteApplicationById(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}
