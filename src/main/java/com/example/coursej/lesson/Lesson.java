package com.example.coursej.lesson;

import com.example.coursej.course.Course;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Lesson extends RepresentationModel<Lesson> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String description;

    private String contentUrl;
    @ManyToOne
    @JsonBackReference
    private Course course;

    @CreationTimestamp
    private LocalDateTime lessonCreationTimestamp;
    @UpdateTimestamp
    private LocalDateTime updateTimestamp ;
}
