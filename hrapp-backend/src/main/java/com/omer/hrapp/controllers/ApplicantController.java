package com.omer.hrapp.controllers;

import com.omer.hrapp.entities.Applicant;
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
    public Applicant getApplicantById(@PathVariable Long applicantId){
        return applicantService.getApplicantById(applicantId);
    }

    @PostMapping
    public Applicant createNewApplicant(@RequestBody Applicant newApplicant){
        return applicantService.createNewApplicant(newApplicant);
    }

    @PutMapping("/{applicantId}")
    public Applicant updateApplicantById(@PathVariable Long applicantId, @RequestBody Applicant newApplicant){
        return applicantService.updateApplicantById(applicantId, newApplicant);
    }

    @DeleteMapping("/{applicantId}")
    public void deleteApplicantById(@PathVariable Long applicantId){
        applicantService.deleteApplicantById(applicantId);
    }
}
