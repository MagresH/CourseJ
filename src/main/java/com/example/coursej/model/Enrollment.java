package com.example.coursej.model;


import com.example.coursej.model.progress.CourseProgress;

import com.example.coursej.model.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Enrollment extends RepresentationModel<Enrollment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Course course;

    @ManyToOne
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    private CourseProgress courseProgress;

    @CreationTimestamp
    private LocalDateTime enrollTimestamp;

}
