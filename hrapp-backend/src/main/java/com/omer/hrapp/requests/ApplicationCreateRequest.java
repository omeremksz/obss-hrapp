package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationCreateRequest {
    private Long id;
    private LocalDateTime appliedDate;
    private String applicationStatus;
    private LocalDateTime lastUpdateTime;
    private Long applicantId;
    private Long jobId;
}
