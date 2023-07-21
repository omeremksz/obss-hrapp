package com.omer.hrapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "application")
public class Application {
    @Id
    private Long id;
    private LocalDateTime appliedDate;
    private Long applicantId;
    private Long jobId;
    private Long applicationStatusId;
}
