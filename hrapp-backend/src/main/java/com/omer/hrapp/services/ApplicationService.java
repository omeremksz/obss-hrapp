package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.entities.Application;
import com.omer.hrapp.entities.Job;
import com.omer.hrapp.repositories.ApplicationRepository;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import com.omer.hrapp.requests.ApplicationUpdateRequest;
import com.omer.hrapp.responses.ApplicationResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private ApplicationRepository applicationRepository;
    private ApplicantService applicantService;
    private JobService jobService;

    private EmailService emailService;

    public ApplicationService(ApplicationRepository applicationRepository, ApplicantService applicantService, @Lazy JobService jobService, EmailService emailService) {
        this.applicationRepository = applicationRepository;
        this.applicantService = applicantService;
        this.jobService = jobService;
        this.emailService = emailService;
    }

    public List<ApplicationResponse> gelAllApplications(Optional<Long> applicantId, Optional<Long> jobId) {
        List<Application> applicationList;
        if(applicantId.isPresent() && jobId.isPresent()){
            applicationList = applicationRepository.findByApplicantIdAndJobId(applicantId.get(), jobId.get());
        } else if (applicantId.isPresent()) {
            applicationList = applicationRepository.findByApplicantId(applicantId.get());
        } else if (jobId.isPresent()) {
            applicationList = applicationRepository.findByJobId(jobId.get());
        } else applicationList = applicationRepository.findAll();
        return applicationList.stream().map(a -> new ApplicationResponse(a)).collect(Collectors.toList());
    }

    public ApplicationResponse getApplicationById(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElse(null);
        return new ApplicationResponse(application);
    }

    public Application createNewApplication(ApplicationCreateRequest applicationCreateRequest) {
        Applicant applicant = applicantService.getApplicantById(applicationCreateRequest.getApplicantId());
        Job job = jobService.getJobById(applicationCreateRequest.getJobId());

        if(applicant == null || job == null){
            return null;
        }
        Application toSave = new Application();
        toSave.setAppliedDate(LocalDateTime.now());
        toSave.setApplicationStatus("Processing");
        toSave.setLastUpdateTime(LocalDateTime.now());
        toSave.setApplicant(applicant);
        toSave.setJob(job);
        return applicationRepository.save(toSave);
    }

    public Application updateApplicationById(Long applicationId, ApplicationUpdateRequest updateApplicationRequest){
        Optional<Application> application = applicationRepository.findById(applicationId);
        if(application.isPresent()){
            Application toUpdate = application.get();
            String applicantName = toUpdate.getApplicant().getFirstName() + " " + toUpdate.getApplicant().getLastName();
            String jobTitle = toUpdate.getJob().getTitle();
            String specialistName = toUpdate.getJob().getSpecialist().getFirstName() + " " + toUpdate.getJob().getSpecialist().getLastName();
            String toEmail = toUpdate.getApplicant().getEmail();
            String applicationStatus = updateApplicationRequest.getApplicationStatus();

            toUpdate.setApplicationStatus(applicationStatus);
            toUpdate.setLastUpdateTime(LocalDateTime.now());

            emailService.sendEmail(toEmail, applicationStatus, applicantName, jobTitle, specialistName);
            return applicationRepository.save(toUpdate);
        }else
            return null;
    }

    public void deleteApplicationById(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    public void deleteApplicationsByBlacklist(Long applicantId, Long specialistId) {
        List<Application> blacklistApplications = applicationRepository.findByApplicantId(applicantId);
        for (Application application :blacklistApplications) {
            if(application.getJob().getSpecialist().getId().equals(specialistId)) {
                applicationRepository.delete(application);
            }
        }
    }

}
