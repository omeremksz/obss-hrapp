package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class JobCreateRequest {
    private Long id;
    private String code;
    private String title;
    private String location;
    private String description;
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private Long specialistId;
    private Long jobCategoryId;
    private Long jobPositionId;
}
