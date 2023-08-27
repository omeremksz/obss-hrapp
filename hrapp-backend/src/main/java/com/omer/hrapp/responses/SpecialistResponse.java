package com.omer.hrapp.responses;

import com.omer.hrapp.entities.Specialist;
import lombok.Data;

import java.util.List;

@Data
public class SpecialistResponse {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private List<JobResponse> jobs;

    public SpecialistResponse(Specialist entity, List<JobResponse> jobs) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.jobs = jobs;
    }
}
