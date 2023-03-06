package com.example.coursej.model;

import jakarta.persistence.*;
import lombok.*;
import static jakarta.persistence.GenerationType.SEQUENCE;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Teacher")
public class Teacher extends User {

    @Id
    @SequenceGenerator(name = "teacher_sequence", sequenceName = "teacher_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "teacher_sequence")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;
}

