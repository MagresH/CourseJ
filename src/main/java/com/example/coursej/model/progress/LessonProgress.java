package com.example.coursej.model.progress;

import com.example.coursej.model.Lesson;
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

    @ManyToOne
    private CourseProgress courseProgress;

    @OneToMany(mappedBy = "lessonProgress",cascade = CascadeType.ALL)
    private List<QuizProgress> quizProgresses;

}
