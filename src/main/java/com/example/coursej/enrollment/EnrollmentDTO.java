package com.example.coursej.enrollment;

import com.example.coursej.course.Course;
import com.example.coursej.progress.courseProgress.CourseProgress;
import com.example.coursej.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Enrollment} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO extends RepresentationModel<EnrollmentDTO> implements Serializable {
    private Long id;

    private Long courseId;
    private Long userId;
    private Long courseProgressId;

}
