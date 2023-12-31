package com.omer.hrapp.requests;

import lombok.Data;

@Data
public class SpecialistUpdateRequest {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
