package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.Application;
import com.omer.hrapp.requests.ApplicationCreateRequest;
import com.omer.hrapp.requests.ApplicationUpdateRequest;
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
    public List<Application> getAllApplications(@RequestParam Optional<Long> applicantId){
        return  applicationService.gelAllApplications(applicantId);
    }

    @PostMapping
    public Application createNewApplication(@RequestBody ApplicationCreateRequest newApplicationRequest){
        return applicationService.createNewApplication(newApplicationRequest);
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
