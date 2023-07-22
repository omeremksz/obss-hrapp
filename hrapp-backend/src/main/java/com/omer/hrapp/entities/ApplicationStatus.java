package com.omer.hrapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_status")
@Data
public class ApplicationStatus {
    @Id
    private Long id;
    private String status;
    private LocalDateTime lastUpdateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="application_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Application application;
}
