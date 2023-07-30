package com.omer.hrapp.responses;

import com.omer.hrapp.entities.Application;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationResponse {
    private Long id;

    private LocalDateTime appliedDate;

    private String applicationStatus;

    private LocalDateTime lastUpdateTime;

    private Long applicantId;

    private Long jobId;

    public ApplicationResponse(Application entity) {
        this.id = entity.getId();
        this.appliedDate = entity.getAppliedDate();
        this.applicationStatus = entity.getApplicationStatus();
        this.lastUpdateTime = entity.getLastUpdateTime();
        this.applicantId = entity.getApplicant().getId();
        this.jobId = entity.getJob().getId();
    }
}
