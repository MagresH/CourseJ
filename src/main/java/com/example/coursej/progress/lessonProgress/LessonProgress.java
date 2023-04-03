package com.example.coursej.progress.lessonProgress;

import com.example.coursej.lesson.Lesson;
import com.example.coursej.progress.Progress;
import com.example.coursej.progress.courseProgress.CourseProgress;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class LessonProgress extends Progress {
    @OneToOne
    private Lesson lesson;

    @JsonBackReference
    @ManyToOne
    private CourseProgress courseProgress;

}
