package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationUpdateRequest {
    private Long id;
    private String applicationStatus;
    private LocalDateTime lastUpdateTime;
    private Long feedbackId;
}
