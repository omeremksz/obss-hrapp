package com.omer.hrapp.controllers;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.requests.ApplicantCreateRequest;
import com.omer.hrapp.requests.ApplicantUpdateRequest;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import com.omer.hrapp.responses.ApplicantResponse;
import com.omer.hrapp.responses.ApplicationResponse;
import com.omer.hrapp.services.ApplicantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {
    private ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping
    public List<Applicant> getAllApplicants(){
        return applicantService.getAllApplicants();
    }

    @GetMapping("/{applicantId}")
    public ApplicantResponse getApplicantById(@PathVariable Long applicantId){
        return applicantService.getApplicantByIdWithApplications(applicantId);
    }

    @PostMapping
    public Applicant createNewApplicant(@RequestBody Applicant newApplicant){
        return applicantService.createNewApplicant(newApplicant);
    }

    @PutMapping("/{applicantId}")
    public Applicant updateApplicantById(@PathVariable Long applicantId, @RequestBody ApplicantUpdateRequest applicantUpdateRequest){
        return applicantService.updateApplicantById(applicantId, applicantUpdateRequest);
    }

    @DeleteMapping("/{applicantId}")
    public void deleteApplicantById(@PathVariable Long applicantId){
        applicantService.deleteApplicantById(applicantId);
    }
}
