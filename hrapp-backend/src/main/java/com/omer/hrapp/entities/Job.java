package com.omer.hrapp.entities;

import jakarta.persistence.*;
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
    @Lob
    @Column(columnDefinition = "text")
    private String description;
    private LocalDateTime activationTime;
    private LocalDateTime deactivationTime;
    private Integer applicantsCount;
    private Long specialistId;
    private Long jobCategoryId;
    private Long jobPositionId;

}
