package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.entities.Application;
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

    public ApplicationService(ApplicationRepository applicationRepository, ApplicantService applicantService) {
        this.applicationRepository = applicationRepository;
        this.applicantService = applicantService;
    }

    public List<Application> gelAllApplications(Optional<Long> applicantId) {
        if(applicantId.isPresent()){
            return applicationRepository.findByApplicantId(applicantId);
        }else
            return applicationRepository.findAll();
    }

    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId).orElse(null);
    }

    public Application createNewApplication(ApplicationCreateRequest newApplicationRequest) {
        Applicant applicant = applicantService.getApplicantById(newApplicationRequest.getApplicantId());
        //Job job = jobService.getJobById(newApplicationRequest.getJobId());

        if(applicant == null){
            return null;
        }
        Application toSave = new Application();
        toSave.setId(newApplicationRequest.getId());
        toSave.setAppliedDate(LocalDateTime.now());
        toSave.setApplicationStatus(newApplicationRequest.getApplicationStatus());
        toSave.setLastUpdateTime(newApplicationRequest.getLastUpdateTime());
        toSave.setApplicant(applicant);
        return applicationRepository.save(toSave);
    }

    public Application updateApplicationById(Long applicationId, ApplicationUpdateRequest updateApplicationRequest){
        Optional<Application> application = applicationRepository.findById(applicationId);
        if(application.isPresent()){
            //Application status should be updated
            return null;
        }else
            return null;
    }

    public void deleteApplicationById(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}
