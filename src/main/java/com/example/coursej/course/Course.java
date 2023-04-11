package com.example.coursej.course;


import com.example.coursej.lesson.Lesson;
import com.example.coursej.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Course {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    @ToString.Exclude
    private List<Lesson> lessons;

    @CreationTimestamp
    private LocalDateTime courseCreationTimestamp;

    @UpdateTimestamp
    private LocalDateTime courseUpdateTimestamp;

}
