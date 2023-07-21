package com.omer.hrapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "feedback")
@Data
public class Feedback {
    @Id
    private Long id;
    private String applicationResults;
    private String explanation;
    private Long applicationStatusId;
    private Long specialistId;
}
