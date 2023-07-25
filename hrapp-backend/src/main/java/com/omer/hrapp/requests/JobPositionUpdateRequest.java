package com.omer.hrapp.requests;

import lombok.Data;

@Data
public class JobPositionUpdateRequest {
    private String code;
    private String name;
    private String description;
}
