package com.omer.hrapp.requests;

import lombok.Data;

@Data
public class JobPositionCreateRequest {
    private Long id;
    private String code;
    private String name;
    private String description;

}
