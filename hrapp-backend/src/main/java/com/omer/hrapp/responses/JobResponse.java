package com.omer.hrapp.responses;

import com.omer.hrapp.entities.Application;
import com.omer.hrapp.entities.Job;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobResponse {
    private Long id;
    private String code;
    private String title;
    private String location;
    private String description;
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private String specialistFirstName;
    private String specialistLastName;
    private Long specialistId;
    private String jobCategory;
    private String jobPosition;
    private List<ApplicationResponse> jobApplications;

    public JobResponse(Job entity, List<ApplicationResponse> jobApplications) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.title = entity.getTitle();
        this.location = entity.getLocation();
        this.description = entity.getDescription();
        this.activationTime = entity.getActivationTime();
        this.deactivationTime = entity.getDeactivationTime();
        this.specialistFirstName = entity.getSpecialist().getFirstName();
        this.specialistLastName = entity.getSpecialist().getLastName();
        this.specialistId = entity.getSpecialist().getId();
        this.jobCategory = entity.getJobCategory();
        this.jobPosition = entity.getJobPosition();
        this.jobApplications = jobApplications;
    }

    public JobResponse(Job entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.title = entity.getTitle();
        this.location = entity.getLocation();
        this.description = entity.getDescription();
        this.activationTime = entity.getActivationTime();
        this.deactivationTime = entity.getDeactivationTime();
        this.specialistFirstName = entity.getSpecialist().getFirstName();
        this.specialistLastName = entity.getSpecialist().getLastName();
        this.specialistId = entity.getSpecialist().getId();
        this.jobCategory = entity.getJobCategory();
        this.jobPosition = entity.getJobPosition();
    }

}
