package com.omer.hrapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application")
@Data
public class Application {
    @Id
    private Long id;

    private LocalDateTime appliedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="applicant_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="job_id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Job job;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="application_status_id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    ApplicationStatus applicationStatus;
}
