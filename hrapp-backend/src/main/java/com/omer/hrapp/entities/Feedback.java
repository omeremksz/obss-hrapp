package com.omer.hrapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {
    @Id
    private Long id;
    private String applicationResults;
    @Lob
    @Column(columnDefinition = "text")
    private String explanation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="application_status_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    ApplicationStatus applicationStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="specialist_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Specialist specialist;
}
