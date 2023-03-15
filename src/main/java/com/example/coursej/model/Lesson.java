package com.example.coursej.model;

import com.example.coursej.model.quiz.Quiz;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

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

    public Lesson(String title, String description, String contentUrl, Course course, List<Quiz> quizzes) {
        this.title = title;
        this.description = description;
        this.contentUrl = contentUrl;
        this.course = course;
        this.quizzes = quizzes;
    }

    private String contentUrl;
    @ManyToOne
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "lesson")
    @JsonBackReference
    private List<Quiz> quizzes;

    @CreationTimestamp
    private LocalDateTime lessonCreationTimestamp;
    @UpdateTimestamp
    private LocalDateTime UpdateTimestamp ;
}
