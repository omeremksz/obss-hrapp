package com.omer.hrapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "job")
@Data
public class Job {
    @Id
    private Long id;
    private String code;
    private String title;
    private String location;
    private String description;
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private Integer applicantsCount;
    private Long specialistId;
    private Long jobCategoryId;
    private Long jobPositionId;

}
