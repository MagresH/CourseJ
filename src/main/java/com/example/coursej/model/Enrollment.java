package com.example.coursej.model;


import com.example.coursej.model.progress.CourseProgress;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class Enrollment extends RepresentationModel<Enrollment> {
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
