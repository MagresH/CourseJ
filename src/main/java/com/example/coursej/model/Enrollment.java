package com.example.coursej.model;


import com.example.coursej.model.progress.CourseProgress;

import com.example.coursej.model.user.Student;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    @JsonBackReference

    private Course course;

    public Enrollment(Course course, Student student, CourseProgress courseProgress) {
        this.course = course;
        this.student = student;
        this.courseProgress = courseProgress;
    }

    @JsonBackReference
    @ManyToOne
    private Student student;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    private CourseProgress courseProgress;

    @CreationTimestamp
    private LocalDateTime enrollTimestamp;
    @UpdateTimestamp
    private LocalDateTime enrollmentUpdateTimestamp;
}
