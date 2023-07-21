package com.omer.hrapp.entities;

import jakarta.persistence.*;
import lombok.Data;

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
    private Long applicationStatusId;
    private Long specialistId;
}
