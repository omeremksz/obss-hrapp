package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class JobCreateRequest {
    private String code;
    private String title;
    private String location;
    private String description;
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private String jobCategory;
    private String  jobPosition;
    private Long specialistId;
}
