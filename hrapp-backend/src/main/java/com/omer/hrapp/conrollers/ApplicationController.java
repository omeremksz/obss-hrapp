package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.Application;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import com.omer.hrapp.requests.ApplicationUpdateRequest;
import com.omer.hrapp.responses.ApplicationResponse;
import com.omer.hrapp.services.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ApplicationResponse> getAllApplications(@RequestParam Optional<Long> applicantId, @RequestParam Optional<Long> jobId){
        return  applicationService.gelAllApplications(applicantId,jobId);
    }

    @GetMapping("/{applicationId}")
    public Application getApplicationById(@PathVariable Long applicationId){
        return applicationService.getApplicationById(applicationId);
    }

    @PostMapping
    public Application createNewApplication(@RequestBody ApplicationCreateRequest applicationCreateRequest){
        return applicationService.createNewApplication(applicationCreateRequest);
    }

    @PutMapping("/{applicationId}")
    public Application updateApplicationById(@PathVariable Long applicationId, @RequestBody ApplicationUpdateRequest updateApplicationRequest){
        return applicationService.updateApplicationById(applicationId, updateApplicationRequest);
    }

    @DeleteMapping("/{applicationId}")
    public void deleteApplicationById(@PathVariable Long applicationId){
        applicationService.deleteApplicationById(applicationId);
    }

}
