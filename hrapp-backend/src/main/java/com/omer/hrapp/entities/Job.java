package com.omer.hrapp.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "job")
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="specialist_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Specialist specialist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="job_category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    JobCategory jobCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="job_Position_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    JobPosition jobPosition;
}
