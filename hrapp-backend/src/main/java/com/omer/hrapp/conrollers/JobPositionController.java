package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.JobPosition;
import com.omer.hrapp.requests.JobPositionCreateRequest;
import com.omer.hrapp.requests.JobPositionUpdateRequest;
import com.omer.hrapp.services.JobPositionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobPositions")
public class JobPositionController {

    JobPositionService jobPositionService;

    public JobPositionController(JobPositionService jobPositionService) {
        this.jobPositionService = jobPositionService;
    }

    @GetMapping
    public List<JobPosition> getAllJobPositions(){
        return jobPositionService.getAllJobPositions();
    }

    @GetMapping("/{jobPositionId}")
    public JobPosition getJobPositionById(@PathVariable Long jobPositionId){
        return jobPositionService.getJobPositionById(jobPositionId);
    }

    @PostMapping
    public JobPosition createNewJobPosition(@RequestBody JobPositionCreateRequest jobPositionCreateRequest){
        return jobPositionService.createNewJobPosition(jobPositionCreateRequest);
    }

    @PutMapping("/{jobPositionId}")
    public JobPosition updateJobPosition(@PathVariable Long jobPositionId, @RequestBody JobPositionUpdateRequest jobPositionUpdateRequest){
        return jobPositionService.updateJobPosition(jobPositionId, jobPositionUpdateRequest);
    }

    @DeleteMapping("/{jobPositionId}")
    public void deleteJobPositionById(@PathVariable Long jobPositionId){
        jobPositionService.deleteJobCategoryById(jobPositionId);
    }
}
