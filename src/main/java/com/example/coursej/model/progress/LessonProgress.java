package com.example.coursej.model.progress;

import com.example.coursej.model.Lesson;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class LessonProgress extends Progress {
    @OneToOne
    private Lesson lesson;

    @JsonBackReference
    @ManyToOne
    private CourseProgress courseProgress;

    public LessonProgress(Boolean completed, Lesson lesson, CourseProgress courseProgress) {
        super(completed);
        this.lesson = lesson;
        this.courseProgress = courseProgress;
    }
}
