package com.omer.hrapp.responses;

import com.omer.hrapp.entities.Job;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class JobResponse {
    private Long id;
    private String code;
    private String title;
    private String location;
    private String description;
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private Integer applicantsCount;
    private Long specialistId;
    private String specialistFirstName;
    private String specialistLastName;
    private Long jobCategoryId;
    private String jobCategoryName;
    private Long jobPositionId;
    private String jobPositionName;

    public JobResponse(Job entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.title = entity.getTitle();
        this.location = entity.getLocation();
        this.description = entity.getDescription();
        this.activationTime = entity.getActivationTime();
        this.deactivationTime = entity.getDeactivationTime();
        this.applicantsCount = entity.getApplicantsCount();
        this.specialistId = entity.getSpecialist().getId();
        this.specialistFirstName = entity.getSpecialist().getFirstName();
        this.specialistLastName = entity.getSpecialist().getLastName();
        this.jobCategoryId = entity.getJobCategory().getId();
        this.jobCategoryName = entity.getJobCategory().getName();
        this.jobPositionId = entity.getJobPosition().getId();
        this.jobPositionName = entity.getJobPosition().getName();
    }
}
