package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobUpdateRequest {
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private String activationStatus;
}
