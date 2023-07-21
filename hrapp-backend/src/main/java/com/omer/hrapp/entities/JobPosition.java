package com.omer.hrapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "job_position")
@Data
public class JobPosition {
    @Id
    private Long id;
    private String code;
    private String name;
    @Lob
    @Column(columnDefinition = "text")
    private String description;

}
