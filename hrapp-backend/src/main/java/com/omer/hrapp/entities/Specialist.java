package com.omer.hrapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "specialist")
@Data
public class Specialist {
    @Id
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
}
