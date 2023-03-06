package com.example.coursej.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Course extends RepresentationModel<Course> {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "course")
    @ToString.Exclude
    private List<Lesson> lessons;

//    @OneToMany(mappedBy = "course")
//    @ToString.Exclude
//    private List<Quiz> quizzes;

    @OneToOne
    private Progress progress;
}
