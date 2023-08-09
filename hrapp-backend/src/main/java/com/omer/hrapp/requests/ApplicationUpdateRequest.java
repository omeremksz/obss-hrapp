package com.omer.hrapp.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationUpdateRequest {
    private String applicationStatus;
}
