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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicationResults;
    @Lob
    @Column(columnDefinition = "text")
    private String explanation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="specialist_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Specialist specialist;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="application_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Application application;
}
