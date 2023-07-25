package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.JobCategory;
import com.omer.hrapp.requests.JobCategoryCreateRequest;
import com.omer.hrapp.requests.JobCategoryUpdateRequest;
import com.omer.hrapp.services.JobCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobCategories")
public class JobCategoryController {

    JobCategoryService jobCategoryService;

    public JobCategoryController(JobCategoryService jobCategoryService) {
        this.jobCategoryService = jobCategoryService;
    }

    @GetMapping
    public List<JobCategory> getAllJobCategories(){
        return jobCategoryService.getAllJobCategories();
    }

    @GetMapping("/{jobCategoryId}")
    public JobCategory getJobCategoryById(@PathVariable Long jobCategoryId){
        return jobCategoryService.getJobCategoryById(jobCategoryId);
    }

    @PostMapping
    public JobCategory createNewJobCategory(@RequestBody JobCategoryCreateRequest jobCategoryCreateRequest){
        return jobCategoryService.createNewJobCategory(jobCategoryCreateRequest);
    }

    @PutMapping("/{jobCategoryId}")
    public JobCategory updateJobCategory(@PathVariable Long jobCategoryId, @RequestBody JobCategoryUpdateRequest jobCategoryUpdateRequest){
        return jobCategoryService.updateJobCategory(jobCategoryId, jobCategoryUpdateRequest);
    }

    @DeleteMapping("/{jobCategoryId}")
    public void deleteJobCategoryById(@PathVariable Long jobCategoryId){
        jobCategoryService.deleteJobCategoryById(jobCategoryId);
    }
}
