package com.example.coursej.model.quiz;

import com.example.coursej.model.Course;
import com.example.coursej.model.Lesson;
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
    @ToString.Exclude
    private List<Question> questions;



    @ManyToOne
    private Lesson lesson;
}
