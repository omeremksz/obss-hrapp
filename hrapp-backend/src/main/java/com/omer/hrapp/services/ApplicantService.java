package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.repositories.ApplicantRepository;
import com.omer.hrapp.requests.ApplicantCreateRequest;
import com.omer.hrapp.requests.ApplicantUpdateRequest;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantService {
    private ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    public Applicant getApplicantById(Long applicantId) {
        return applicantRepository.findById(applicantId).orElse(null);
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
