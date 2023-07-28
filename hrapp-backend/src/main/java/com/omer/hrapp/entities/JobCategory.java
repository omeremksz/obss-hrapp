package com.omer.hrapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "job_category")
@Data
public class JobCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @Lob
    @Column(columnDefinition = "text")
    private String description;
}
