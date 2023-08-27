package com.omer.hrapp.responses;

import com.omer.hrapp.entities.Applicant;
import lombok.Data;

import java.util.List;

@Data
public class ApplicantResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String profilePhotoUrl;
    private String linkedInProfileURL;
    private String email;
    private String about;
    private List<ApplicationResponse> applications;

    public ApplicantResponse(Applicant entity, List<ApplicationResponse> applications) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.profilePhotoUrl = entity.getProfilePhotoUrl();
        this.linkedInProfileURL = entity.getLinkedInProfileURL();
        this.email = entity.getEmail();
        this.about = entity.getAbout();
        this.applications = applications;
    }
}
