package com.omer.hrapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="specialist_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Specialist specialist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="job_category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    JobCategory jobCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="job_Position_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    JobPosition jobPosition;

}
