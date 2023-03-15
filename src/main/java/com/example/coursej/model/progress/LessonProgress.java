package com.example.coursej.model.progress;

import com.example.coursej.model.Lesson;
import com.example.coursej.model.quiz.QuizProgress;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class LessonProgress extends Progress {
    @OneToOne
    private Lesson lesson;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonBackReference
    @ManyToOne
    private CourseProgress courseProgress;

    @OneToMany(mappedBy = "lessonProgress",cascade = CascadeType.ALL)
    @JsonBackReference
    @ToString.Exclude
    private List<QuizProgress> quizProgresses;

    public LessonProgress(Boolean completed, Lesson lesson, CourseProgress courseProgress, List<QuizProgress> quizProgresses) {
        super(completed);
        this.lesson = lesson;
        this.courseProgress = courseProgress;
        this.quizProgresses = quizProgresses;
    }
}
