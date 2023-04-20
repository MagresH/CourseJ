package com.example.coursej.enrollment;


import com.example.coursej.course.Course;
import com.example.coursej.progress.courseProgress.CourseProgress;
import com.example.coursej.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Enrollment  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JsonBackReference
    private Course course;

    public Enrollment(Course course, User user, CourseProgress courseProgress) {
        this.course = course;
        this.user = user;
        this.courseProgress = courseProgress;
    }

    @JsonBackReference
    @ManyToOne
    private User user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(cascade = CascadeType.ALL)
    private CourseProgress courseProgress;

    @CreationTimestamp
    private LocalDateTime enrollCreationTimestamp;

    @UpdateTimestamp
    private LocalDateTime enrollmentUpdateTimestamp;
}
