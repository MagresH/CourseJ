package com.example.coursej.model.progress;

import com.example.coursej.model.Enrollment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class CourseProgress extends Progress {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private Enrollment enrollment;

    public CourseProgress(Boolean completed, Enrollment enrollment, List<LessonProgress> lessonProgresses) {
        super(completed);
        this.enrollment = enrollment;
        this.lessonProgresses = lessonProgresses;
    }
    @JsonBackReference
    @OneToMany(mappedBy = "courseProgress",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<LessonProgress> lessonProgresses;
}
