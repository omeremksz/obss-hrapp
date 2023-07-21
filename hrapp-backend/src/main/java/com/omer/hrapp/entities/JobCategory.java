package com.omer.hrapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "job_category")
@Data
public class JobCategory {
    @Id
    private Long id;
    private String code;
    private String name;
    private String description;
}
