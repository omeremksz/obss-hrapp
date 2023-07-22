package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.entities.Application;
import com.omer.hrapp.entities.ApplicationStatus;
import com.omer.hrapp.repositories.ApplicationRepository;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import com.omer.hrapp.requests.ApplicationUpdateRequest;
import org.springframework.stereotype.Service;

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

    public Application createNewApplication(ApplicationCreateRequest newApplicationRequest) {
        Applicant applicant = applicantService.getApplicantById(newApplicationRequest.getApplicantId());
        //Job job = jobService.getJobById(newApplicationRequest.getJobId());
        //ApplicationStatus applicationStatus = applicationStatusService.getApplicationStatusById(newApplicationRequest.getApplicationStatusId());

        if(applicant == null){
            return null;
        }
        Application toSave = new Application();
        toSave.setId(newApplicationRequest.getId());
        toSave.setAppliedDate(newApplicationRequest.getAppliedDate());
        toSave.setApplicant(applicant);
        return applicationRepository.save(toSave);
    }

    public Application updateApplicationById(Long applicationId, ApplicationUpdateRequest updateApplicationRequest) {
        return null;
//        Optional<Application> application = applicationRepository.findById(applicationId);
//        if(application.isPresent()){
//            Application toUpdate = application.get();
//            ApplicationStatus applicationStatus = applicationStatusService.getApplicationStatusById(updateApplicationRequest.getApplicationStatusId());
//            toUpdate.setApplicationStatus(applicationStatus);
//            return applicationRepository.save(toUpdate);
//        }else
//            return null;
    }

    public void deleteApplicationById(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}
