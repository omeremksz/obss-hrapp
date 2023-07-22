package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationCreateRequest {
    Long id;
    LocalDateTime appliedDate;
    Long applicantId;
    Long jobId;
    Long applicationStatusId;
}
