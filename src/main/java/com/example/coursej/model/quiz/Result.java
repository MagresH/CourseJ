package com.example.coursej.model.quiz;

import com.example.coursej.model.progress.QuizProgress;
import com.example.coursej.model.user.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Result extends RepresentationModel<Result> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private QuizProgress quizProgress;

    private Integer score;
}
