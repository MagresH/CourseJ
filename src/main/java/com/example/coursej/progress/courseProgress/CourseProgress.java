package com.example.coursej.progress.courseProgress;

import com.example.coursej.enrollment.Enrollment;
import com.example.coursej.progress.Progress;
import com.example.coursej.progress.lessonProgress.LessonProgress;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class CourseProgress extends Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private Enrollment enrollment;

    @JsonBackReference
    @OneToMany(mappedBy = "courseProgress",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<LessonProgress> lessonProgresses;


}
