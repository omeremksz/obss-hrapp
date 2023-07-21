package com.omer.hrapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_status")
@Data
public class ApplicationStatus {
    @Id
    private long id;
    private String status;
    private LocalDateTime lastUpdateTime;
    private Long applicationId;
}
