package com.example.coursej.model.progress;

import com.example.coursej.model.Lesson;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
