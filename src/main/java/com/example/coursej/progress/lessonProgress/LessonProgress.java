package com.example.coursej.progress.lessonProgress;

import com.example.coursej.lesson.Lesson;
import com.example.coursej.progress.Progress;
import com.example.coursej.progress.courseProgress.CourseProgress;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class LessonProgress extends Progress {
    @OneToOne
    private Lesson lesson;

    @ManyToOne
    private CourseProgress courseProgress;



}
