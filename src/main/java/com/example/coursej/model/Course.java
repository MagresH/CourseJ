package com.example.coursej.model;

import com.example.coursej.model.progress.Progress;
import com.example.coursej.model.user.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Course extends RepresentationModel<Course> {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    public Course(String title, String description, Teacher teacher, List<Lesson> lessons) {
        this.title = title;
        this.description = description;
        this.teacher = teacher;
        this.lessons = lessons;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Teacher teacher;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    @ToString.Exclude
    private List<Lesson> lessons;

    @CreationTimestamp
    private LocalDateTime courseCreationTimestamp;
    @UpdateTimestamp
    private LocalDateTime courseUpdateTimestamp;
}
