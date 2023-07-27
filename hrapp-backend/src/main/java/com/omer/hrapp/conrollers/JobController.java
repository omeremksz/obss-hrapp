package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.Job;
import com.omer.hrapp.requests.JobCreateRequest;
import com.omer.hrapp.requests.JobUpdateRequest;
import com.omer.hrapp.services.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {
    JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping("/{jobId}")
    public Job getJobById(@PathVariable Long jobId){
        return jobService.getJobById(jobId);
    }

    @GetMapping
    public List<Job> getAllJobs(@RequestParam Optional<Long> jobCategoryId, @RequestParam Optional<Long> jobPositionId){
        return jobService.getAllJobs(jobCategoryId, jobPositionId);
    }

    @PostMapping
    public Job createNewJob(@RequestBody JobCreateRequest jobCreateRequest){
        return jobService.createNewJob(jobCreateRequest);
    }

    @PutMapping("/{jobId}")
    public Job updateJob(@PathVariable Long jobId, @RequestBody JobUpdateRequest jobUpdateRequest){
        return jobService.updateJob(jobId, jobUpdateRequest);
    }

    @DeleteMapping("/{jobId}")
    public void deleteJobById(@PathVariable Long jobId){
        jobService.deleteJobById(jobId);
    }
}