package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.services.ApplicantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    private ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
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
