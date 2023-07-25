package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobUpdateRequest {
    private String code;
    private String title;
    private String location;
    private String description;
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private Integer applicantsCount;
    private Long jobCategoryId;
    private Long jobPositionId;
}
