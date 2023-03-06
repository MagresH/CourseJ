package com.example.coursej.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Course {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

}
