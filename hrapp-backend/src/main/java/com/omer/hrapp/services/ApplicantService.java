package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.repositories.ApplicantRepository;
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

    public Applicant createNewApplicant(Applicant newApplicant) {
        return applicantRepository.save(newApplicant);
    }

    public Applicant updateApplicantById(Long applicantId, Applicant newApplicant) {
        Optional<Applicant> applicant = applicantRepository.findById(applicantId);
        if(applicant.isPresent()){
            Applicant foundApplicant = applicant.get();
            foundApplicant.setFirst_name(newApplicant.getFirst_name());
            foundApplicant.setLast_name(newApplicant.getLast_name());
            foundApplicant.setEmail(newApplicant.getEmail());
            foundApplicant.setAbout(newApplicant.getAbout());
            applicantRepository.save(foundApplicant);
            return foundApplicant;
        }else
            return null;
    }

    public void deleteApplicantById(Long applicantId) {
        applicantRepository.deleteById(applicantId);
    }
}
