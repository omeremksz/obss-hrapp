package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationCreateRequest {
    private Long applicantId;
    private Long jobId;
}
