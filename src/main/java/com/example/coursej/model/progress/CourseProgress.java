package com.example.coursej.model.progress;

import com.example.coursej.model.Enrollment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class CourseProgress extends Progress {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private Enrollment enrollment;

    @JsonBackReference
    @OneToMany(mappedBy = "courseProgress",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<LessonProgress> lessonProgresses;


}
