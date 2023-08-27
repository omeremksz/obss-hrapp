package com.omer.hrapp.requests;

import lombok.Data;

@Data
public class BlacklistCreateRequest {
    private String explanation;
    private Long applicantId;
    private Long specialistId;
}
