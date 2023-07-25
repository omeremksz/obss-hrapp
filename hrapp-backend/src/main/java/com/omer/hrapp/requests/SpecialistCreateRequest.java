package com.omer.hrapp.requests;

import lombok.Data;

@Data
public class SpecialistCreateRequest {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
}
