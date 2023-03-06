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
public class Quiz extends RepresentationModel<Quiz> {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Result> results;

    @ManyToOne
    private Lesson lesson;
}
