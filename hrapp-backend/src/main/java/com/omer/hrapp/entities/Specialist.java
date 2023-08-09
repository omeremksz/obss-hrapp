package com.omer.hrapp.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "specialist")
@Data
public class Specialist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    private String email;
}
