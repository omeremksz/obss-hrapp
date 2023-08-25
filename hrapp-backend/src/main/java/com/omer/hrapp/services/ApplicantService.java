package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.repositories.ApplicantRepository;
import com.omer.hrapp.requests.ApplicantCreateRequest;
import com.omer.hrapp.requests.ApplicantUpdateRequest;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import com.omer.hrapp.responses.ApplicantResponse;
import com.omer.hrapp.responses.ApplicationResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantService {

    private ApplicationService applicationService;
    private ApplicantRepository applicantRepository;

    public ApplicantService(@Lazy ApplicationService applicationService, ApplicantRepository applicantRepository) {
        this.applicationService = applicationService;
        this.applicantRepository = applicantRepository;
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Applicant getApplicantById(Long applicantId) {
        return applicantRepository.findById(applicantId).orElse(null);
    }
    public ApplicantResponse getApplicantByIdWithApplications(Long applicantId) {
        Applicant applicant = applicantRepository.findById(applicantId).orElse(null);
        List<ApplicationResponse> applications = applicationService.gelAllApplications(Optional.of(applicantId), Optional.ofNullable(null));
        return new ApplicantResponse(applicant, applications);
    }

    public Optional<Applicant> getApplicantByEmail(String email) {
        return applicantRepository.findByEmail(email);
    }

    public Applicant createNewApplicant(Applicant newApplicant) {
        return applicantRepository.save(newApplicant);
    }

    public Applicant updateApplicantById(Long applicantId, ApplicantUpdateRequest applicantUpdateRequest) {
        Optional<Applicant> applicant = applicantRepository.findById(applicantId);
        if(applicant.isPresent()){
            Applicant foundApplicant = applicant.get();
            foundApplicant.setLinkedInProfileURL(applicantUpdateRequest.getLinkedInProfileURL());
            applicantRepository.save(foundApplicant);
            return foundApplicant;
        }else
            return null;
    }

    public void deleteApplicantById(Long applicantId) {
        applicantRepository.deleteById(applicantId);
    }
}
