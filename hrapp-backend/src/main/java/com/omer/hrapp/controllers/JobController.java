package com.omer.hrapp.controllers;

import com.omer.hrapp.entities.Job;
import com.omer.hrapp.requests.JobCreateRequest;
import com.omer.hrapp.requests.JobUpdateRequest;
import com.omer.hrapp.responses.JobResponse;
import com.omer.hrapp.services.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{jobId}")
    public JobResponse getJobById(@PathVariable Long jobId){
        return jobService.getJobByIdWithApplications(jobId);
    }

    @GetMapping
    public List<JobResponse> getAllJobsBySpecialistId(@RequestParam Optional<Long> specialistId){
        return jobService.getAllJobsBySpecialistId(specialistId);
    }

    @PostMapping
    public Job createNewJob(@RequestBody JobCreateRequest jobCreateRequest){
        return jobService.createNewJob(jobCreateRequest);
    }

    @PutMapping("/{jobId}")
    public Job updateJobById(@PathVariable Long jobId, @RequestBody JobUpdateRequest jobUpdateRequest){
        return jobService.updateJobById(jobId, jobUpdateRequest);
    }

    @DeleteMapping("/{jobId}")
    public void deleteJobById(@PathVariable Long jobId){
        jobService.deleteJobById(jobId);
    }
}
