package com.omer.hrapp.requests;

import lombok.Data;

@Data
public class ApplicantCreateRequest {
    private String firstName;
    private String lastName;
    private String profilePhotoUrl;
    private String linkedInProfileURL;
    private String email;
}
