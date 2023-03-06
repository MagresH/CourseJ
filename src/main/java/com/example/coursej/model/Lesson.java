package com.example.coursej.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Lesson extends RepresentationModel<Lesson> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String description;

    private String contentUrl;
    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<Quiz> quizzes;


}
