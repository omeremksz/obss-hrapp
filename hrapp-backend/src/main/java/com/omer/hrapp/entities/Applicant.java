package com.omer.hrapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "applicant")
@Data
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String profilePhotoUrl;
    private String linkedInProfileURL;
    private String email;
    @Lob
    @Column(columnDefinition = "text")
    private String about;

}
