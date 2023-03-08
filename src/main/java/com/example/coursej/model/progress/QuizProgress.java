package com.example.coursej.model.progress;

import com.example.coursej.model.quiz.Result;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class QuizProgress extends Progress {

    @ManyToOne
    private LessonProgress lessonProgress;

    @OneToMany(mappedBy = "quizProgress", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Result> results;
}
