package com.example.coursej.model.progress;

import com.example.coursej.model.Enrollment;
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

    @OneToOne
    private Enrollment enrollment;

    @OneToMany(mappedBy = "courseProgress",cascade = CascadeType.ALL)
    private List<LessonProgress> lessonProgresses;
}
