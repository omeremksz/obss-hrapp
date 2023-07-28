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

    private String first_name;

    private String last_name;

    private String email;

    @Lob
    @Column(columnDefinition = "text")
    private String about;

}
